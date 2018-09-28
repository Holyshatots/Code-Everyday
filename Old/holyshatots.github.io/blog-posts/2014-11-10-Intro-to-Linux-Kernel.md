---
layout: post
title: Introduction to the Linux Kernel
date:   2014-11-10 00:00:00
---

### Overview of the Operating Systems and Kernels

Kernel: software that provides basic services for all other parts of the system,
manages hardware, and distributes system resources.

Applications running on the system communicate with the kernel via __system calls__.
When hardware wants to communicate with the system, it issues an interrupt that
literally interrupts the processor, which in turn interrupts the kernel.
A number identifies the interrupt and kernel events the specific interrupt 
handler for that number. Interrupt handlers run in a special __interrupt context__ which allows them
to quickly respond to an interrupt and exit.

A processor is doing exactly one of three things at any given moment:

1. In user-space, executing user code in a process
2. In kernel-space, in process context, executing on behalf of a specific process
3. In kernel-space, in interrupt context, not associated with a process, handling
an interrupt.

### Monolithic Kernel vs Microkernel

Monolithic kernel: Implemented as a single process running in a single address
space. 

- Most Unix systems based on this design
- Linux is a monolithic kernel; borrows the good from microkernels (modular design,
capability to preempt itself, support for kernel threads, and the capability
to dynamically load separate binaries (load modules) into the kernel image.)

Microkernel: The functionality of the kernel is broken down into separate
processes, typically called servers. All servers run in separate address spaces.
This requires message passing (An Interprocess mechanism (IPM) is built into the
system and invoke "services" from each other) because they cannot directly
communicate.

- Prevents failure in one server from bringing down another.
- Requires more overhead
- WIndows NT kernel (Windows XP, Vista, and 7)
- Mach (Mac OSX)
  - Neither run any microkernel servers in user space.

### Linux versus Classic Unix Kernels

- Linux supports the dynamic loading of kernel modules
- Linux has symmetrical multiprocessor (SMP) support.
- Linux kernel is preemptive
- Linux doesn't differentiate between threads and normal processes. To the kernel,
- all processes are the same, some just happen to share resources.
- Linux provides an object-oriented device model with device classes, hot-pluggable
events, and a user-space device filesystem (sysfs).
- Linux ignores some common Unix features that the kernel developers consider
poorly designed.

### Linux Kernel Versions

__Stable kernels production__: level releases suitable for widespread deployment

__Development kernels__: undergo rapid change and the code base often changes in 
drastic ways.

### A Beast of a different nature

The most important differences between the kernel and normal user-space applications:

- The kernel has access to neither the C library nor the standard C headers
- The kernel is coded in GNU C
- The kernel lacks the memory protection afforded to user-space
- The kernel cannot easily execute floating-point operations
- The kernel has a small per-process fixed-size stack.
- Because the kernel has asynchronous interrupt, is preemptive, and supports
- SMP, synchronization, and concurrency are major concerns within the kernel.
- Portability is important

### No libc or standard headers

Kernel isn't linked to the standard C library. The usual libc functions are implemented with the kernel.
Certain functions require you to go lower than you usually would because there is not full access 
to the standard library.

### GNU C

Kernel developers use both ISO C94 and GNU C extensions to the C language.

Interesting extensions in the kernel:

- __Inline functions__ are inserted inline into each function call site.
- __Inline assembly__ is the use of assembly instructions in the code. Only
low-level architecture and fast. Path code uses this.
- __Branch annotation__ is marking one condition of a branch as likely and the
other as unlikely. This gives a performance boost to the likely branch but
impacts the other conditional performance.
- __No memory protection__ means that if the kernel attempts to access illegal
memory, the results will be unpredictable.
- __No (easy) use of floating point__
- __Small, fixed-size stack__ : The kernel isn't large or dynamic so the kernel's
stack is limited to ~4-8 kb
- __Synchronization and concurrency__ : The kernel is susceptible to race conditions
- __Importance of portability__ : Architecture independent C must be written
to achieve portability.

