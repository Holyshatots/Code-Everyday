---
content: post
title: Linux Kernel Development
---

## Introduction to the Linux kernel

### Overview of Operating Systems and Kernels

Kernel: software that provides basic services for all other parts of the system, manages hardware, and distributes system resources. 
- Applications running on the system communicate with the kernel via __system calls__.
- When hardware wants to communicate with the system, it issues an itnerrupt that literally interrupts the processor which in turn interrupts the kernel
  - A number identifies the interrupt and kernel executes the specific interrupt handler for that number 
  Interrupt handler run in a special __interrupt context__ which allows them to quickly respond to an interrupt and exit.

A processor is doing exactly one of three things at any given movement:
- In user-space, executing user code in a process
- In kernel-space, in process context, executing on behalf of a specific process.
- In kernel-space, in interrupt context, not associated with a process, halding an interrupt

### Monolithic Kernel vs Microkernel

__Monolithic kernel__: Implemented as a single porcess running in a single address space.
- Most Unix systems based on this design
- Linux is a monlithic kernel; borrows the good from microkernels (modular design, capability to preempt itself, support for kernel threads, and the capability to dynamically load seperate binaries (kernel moduels) into the kernel image).

__Microkernel__: The functionality of the kernel is broken down into seperate processes, typically called servers. All servers run in sepearte address spaces. This requires message passing (An Interprocess mechanism (IPC) mechanism is built into the system and invoke "services" from each other) because they cannot directly communicate.
- Prevents failure in one server from bringing down another
- Requires more overhead
- Windows NT kernel (Windows Xp, Vista, and 7)
- Mach (Mac OSX)
  - Neither run any microkernel servers in user space.

### Linux versus Classic Unix kernels

- Linux supports the dynamic loading of kernel modules
- Linux has symmetrical multiprocessor (SMP) support.
- Linux kernel is preemptive
- Linux doesn't differentiate between threads and normal processes. To the kernel all processes are the same, some just happen to share resources.
- Linux provides an object-oriented device model with device classes, hot-pluggable events, and a user-space device filesystem (sysfs)
- Linux ignores some common Unix features that the kernel developers consider poorly designed

### Linux kernel versions

Stable kernels production level releases suitable for widespread deployment.

Development kernels: Undergo rapid change and the code base often changes in drastic ways

### A Beast of a different Nature

The most important differences between the kernel and normal user-space applications:
- The kernel has access to neither the C library nor the standard C headers.
- The kernel is coded in GNU C
- The kernel lacks the memenv protection afforded to user-space
- The kernel cannot easily execute floating-point operations
- The kernel has a small per-process fixed-size stack.
- Because the kernel has asynchronous interrupt, is preemptive, and supports SMP, synchronization and concurrency are major concerns within the kernel
- Portability is important

### No libc or standard headers

- Kernel isn't linked to the standard C library
- The usual libc functions are implemented within the kernel

### GNU C
Kernel developers use both ISO and GNU C extensions to the C language. Interesting extensions in the kernel:
- __Inline functions__ is inserted inline onto each function call site
- __Inline asembly__ is the use of assembly instructions in the code. Only low-level architecture and fast path code uses this
- __Branch annotation__ is marking one condition of a branch as likely and the other as unlikely. This gives a performance boost to the likely branch but impacts the other conditions performance.
- __No memory protection__ means that if the kernel attempts illegal memory, the result will be unpredictable
- __No (easy) use of floating point__
- __Small, fixed-size stack__ : The kernel isn't large or dynamic so the kernel's stock is limited to ~4-8 KB
- __Synchronization and concurrency__ : The kernel is susceptible to race conditions
- __Importance of portability__ : Architecture independent C must be written to acheive portability