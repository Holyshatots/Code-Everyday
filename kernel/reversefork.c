/*
 * Mitch McAffee
 * Uses fork() that share a file descriptor
 * One continously writes strings to the device
 * The other reads them
 * This is an example of a race condition if the kernel module didn't use a mutex
*/

int main(int argc, char *argv[]){
	char *phrase = "I am little teapot. Short and stout.";
	if(fork())
		//Parent
		while(1) {
			write(fd, phrase, len);
		}
	else
		//Child
		while(1){
			read(fd, buf, len);
			printf("Read: %s\n", buf);
		}
}
