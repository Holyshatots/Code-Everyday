/*
Mitch McAffee
*******************************************************************
http://www.linuxvoice.com/be-a-kernel-hacker/?pk_campaign=hn&pk_kwd=3
* -----------------------------------------------------------------------
* To avoid root:
*  Create a /lib/udev/rules.d/99-reverse.rules file that contains:
* SUBSYSTEM=="misc", KERNEL=="reverse", MODE="0666
*******************************************************************
* Navigating the Linux kernel:
* run: make cscope && cscope
* in the kernel sources' top-level directory
* or for non-terminal:
* http://lxr.free-electrons.com/
* ******************************************************************
* Debugging kernel code:
* - Print using printk()
* - Use pr_debug() or dev_dbg(), if writing a device driver that has its own struct device
* - pr_devel()
* 	- no-op unless DEBUG is defined
* 	- To enable include in the Makefile:
* 		CFLAGS_reverse.o := --DDEBUG
* - Print debuf messages directly to the console through:
* 	- Set the console_loglevel kernel variable to 8 or greater:
* 		echo 8 > /proc/sys/kernel/printk
*   - Temporarily print the debuf message at the high log level like KERN_ERR
*/

#include <linux/init.h> /* __init and __exit macroses */
#include <linux/kernel.h> /* KERN_INFO macros */
#include <linux/module.h> /* required for all kernel modules */
#include <linux/moduleparam.h> /* module_param() and MODULE_PARM_DESC() */

#include <linux/fs.h> /* struct file_operations, struct file */
#include <linux/miscdevice.h> /* struct miscdevice and misc_[de]register() */
#include <linux/mutex.h> /* mutexes */
#include <linux/string.h> /* memchr() function */
#include <linux/slab.h> /* kzalloc() function */
#include <linux/sched.h> /* wait queues */
#include <linux/uaccess.h> /* copy_{to,from}_user() */

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Mitchell McAffee <mitch.mcaffee@gmail.com>");
MODULE_DESCRIPTION("In-kernel phrase reverser");

static unsigned long buffer_size = 8192;
module_param(buffer_size, ulong, (S_IRUSR | S_IRGRP | S_IROTH));
MODULE_PARM_DESC(buffer_size, "Internal buffer size"); // Visible through the modinfo command
// the modinfo command displays all parameters accepted by the module (also avaible under /sys/module//parameters)

struct buffer {
	wait_queue_head_t read_queue;
	struct mutex lock;
	char *data, *end; // end is the first byte after the string end, data is a pointer to the string this buffer stores
	char *read_ptr; // read_ptr is where read() should start reading the data from
	unsigned long size;
};


static struct miscdevice reverse_misc_device = {
	.minor = MISC_DYNAMIC_MINOR,
	.name = "reverse",
	.fops = &reverse_fops
};	

static struct file_operations reverse_fops = {
	.owner = THIS_MODULE,
	.open = reverse_open,
	.read = reverse_read,
	.write = reverse_write,
	.release = reverse_close,
	.llseek = noop_llseek //Set of callbacks to be executed when userspace code messes with the file descriptor
};

static struct buffer *buffer_alloc(unsigned long size) // Allocate space for buffer
{
	struct buffer *buf;
	buf = kzalloc(sizeof(*buf), GFP_KERNEL); // Sets the memory to all-zeroes,												
	if(unlikely(!buf)) //GFP_KERNEL means we need a normal kernel memory and it can sleep if needed
		goto out;
		
	buf->data = kzalloc(size, GFP_KERNEL);
	if (unlikely(!buf->data)) //unlikely is used when the condition is almost always false and help boost performance
	goto out_free;

	init_waitqueue_head(&buf->read_queue); //Wait for data to be available

	mutex_init(&buf->lock);

	/* It's unused for now, but may appear useful later */
	buf->size = size;

	out:
		return buf;

	out_free:
		kfree(buf); // Free kernel memory
		return NULL;
}

static void buffer_free(struct buffer *buffer) //Frees the buffer data
{
	kfree(buffer->data);
	kfree(buffer);
}

static int reverse_open(struct inode *inode, struct file *file) 
/* 
 * struct file is a standard kernel data structre that stores information about an opened file
*/
{
	struct buffer *buf;
	int err = 0;

	/*
	* Real code can use inode to get pointer to the private
	* device state.
	*/

	buf = buffer_alloc(buffer_size);
	if (unlikely(!buf)) {
		err = -ENOMEM; // Indicate the buffer allocation has failed by calling returning a negative value
		goto out;
	}

	file->private_data = buf;

	out:
		return err;
}

static char *reverse_phrase(char *start, char *end)
{
	char *word_start = start, *word_end = NULL;

	while ((word_end = memchr(word_start, ' ', end - word_start)) != NULL) {
		reverse_word(word_start, word_end - 1);
		word_start = word_end + 1;
	}

	reverse_word(word_start, end);

	return reverse_word(start, end);
}

static int reverse_close(struct inode *inode, struct file *file)
{
	struct buffer *buf = file->private_data;
	buffer_free(buf);
	return 0;
}



static int _init reverse_init(void) // Function to be called on the module's insertion
{                                   // Required function
	if (!buffer_size) // Sanitise buffer_size because it can be set by the user
		return -1;
	misc_register(&reverse_misc_device); // Here we request a first available (dnyamic) minor number for the device name "reverse"
	printk(KERN_INFO "reverse device has been registered, buffer size is %lu bytes\n", buffer_size); // Prints a message to the kernel ring buffer
	// KERN_INFO is a log level
	// _init and _exit are attributes (pieces of metadata attached to functions(or variables))
	
	return 0; //Kernel ring buffer accessable from userspace through dmesg
}

static void _exit reverse_exit(void) // Function to be called on module's removal
{									 // Not required
	misc_deregister(&reverse_misc_device); //Unregister the device on the module's teardown
	printk(KERN_INFO "reverse device has been unregistered\n");
}

static ssize_t reverse_read(struct file *file, char __user * out, size_t size, loff_t * off)
// Copies the data from the kernel buffer into the userspace
{
	struct buffer *buf = file->private_data;
	ssize_t result;

	if (mutex_lock_interruptible(&buf->lock)) { //Mutex allows only one function to "hold" an object
		// This grabs the mutex and returns or puts the process to sleep until the mutex is available
		result = -ERESTARTSYS; // -ERESTARTSYS means the system call should be restarted
		goto out;
	}

	while (buf->read_ptr == buf->end) {
		mutex_unlock(&buf->lock);
		if (file->f_flags & O_NONBLOCK) {
			result = -EAGAIN; // Returned if there is no data
			goto out;
		}
		if (wait_event_interruptible(buf->read_queue, buf->read_ptr != buf->end)) {
			result = -ERESTARTSYS;
			goto out;
		}
		if (mutex_lock_interruptible(&buf->lock)) {
			result = -ERESTARTSYS;
			goto out;
		}
	}

	size = min(size, (size_t) (buf->end - buf->read_ptr)); // Copy the data from buf->data to the userspace
	if (copy_to_user(out, buf->read_ptr, size)) { //This waits for the data so that the mutex isn't held and a deadlock doesn't occur
		result = -EFAULT; // Fails if the user space pointer is wrong
		goto out_unlock;
	}

	buf->read_ptr += size;
	result = size;

	 out_unlock:
		mutex_unlock(&buf->lock);
	 out:
		return result;
}

static inline char *reverse_word(char *start, char *end)
{
	char *orig_start = start, tmp;

	for (; start < end; start++, end--) {
		tmp = *start;
		*start = *end;
		*end = tmp;
	}

	return orig_start;
}

static ssize_t reverse_write(struct file *file, const char __user * in, size_t size, loff_t * off)
{
	struct buffer *buf = file->private_data;
	ssize_t result;

	if (size > buffer_size) { //Check that the buffer has enough space
		result = -EFBIG;
		goto out;
	}

	if (mutex_lock_interruptible(&buf->lock)) {
	result = -ERESTARTSYS;
	goto out;
	}

	if (copy_from_user(buf->data, in, size)) { // Get the data
		result = -EFAULT;
	goto out_unlock;
	}

	buf->end = buf->data + size; // reset end pointer 
	buf->read_ptr = buf->data; // reset read_ptr
	if (buf->end > buf->data)
		reverse_phrase(buf->data, buf->end - 1); // reverse the contents of buffer

	wake_up_interruptible(&buf->read_queue); // Wake up processes waiting for the data at read_queue

	result = size;
	
	out_unlock:
		mutex_unlock(&buf->lock);
	out:
		return result;
}

module_init(reverse_init);
module_exit(reverse_exit);

