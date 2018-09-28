---
layout: post
title: Windows Concepts and Tools
date:   2014-08-14 00:00:00
---

## Foundation concepts and terms

- Windows API: The user-mode system programming interface to the WIndows operating system family.
- Windows API's callable functions divided into these categories:
  - Base Services
  - Component Services
  - User Interface Services
  - Graphics and Multimedia services
  - Messaging and Collaboration
  - Networking
  - Web Services

## Services, Functions, and Routines

- Windows API Functions: Documented, callable subroutines in the Windows API. Examples: CreateProcess, CreateFile, and Get Message
- Native system services ( or system calls): The undocumented, underlying services in the operating system that are capable from user mode. Example: NTCreateUserProcess is the internal system service the Windows CreateProcess functions calls to create a new process.
- Kernel support functions (or routines): Subroutines inside the Windows operating system that can be called only from kernel mode. Example: ExAllocatePoolWithTag is the routine that device drivers call to allocate memory from the Windows system heaps (called pools).
- Windows services: Processes started by the Windows service control manager. Example: the Task Scheduler service runs in a user-mode process that supports the at command (which is similar the the UNIX commands at or cron)
- DLLs (Dynamic Link Libraries): A set of callable subroutines linked together as a binary file that can be dynamically loaded applications that use subrouteines. Example: Msvcrt.dll (the C runtime library) and kernel32.dll (one of the Windows API subsystem libraries). Windows user-mode components and applications use DLLs extensively. The advantage over static libraries is that applications can share DLLs, and Windows ensures that there is only on in-memory copy of a DLL's code.

## Processes, Threads, and Jobs

- Program: static sequence of instructions
- Process: a container for a set of resources used when executing the instance of a program

A Windows process comprises of:

- A private virtual address space: a set of virtual memory addresses that the process can use
- An executable program: defines initial code and is mapped into the process' virtual address space
- A list of open handles to various system resources (semaphores, communication ports, files) that are accessible to all threads in the process.
- A security context called on access token that identifies the user, security groups, privileges, User Account Control (UAC), virtualization state, session, and limited user account state associated with the process.
- A unique identifier called a process ID
- At least one thread of execution

To view information about processes and threads use _Process Explorer_ from SysInternals.

A thread is the entity within a process that Windows schedules for execution. Without it, the process' program can't run. A thread includes the following essential components:

- The contents of a set of CPU registers representing the state of the processor.
- Two stacks: one for the thread to use while executing in kernel mode and one for executing in user mode.
- A private storage area called thread-local storage (TLS) for user by subsystems, run-time libraries, and DLLs.
- A unique identifier called a thread ID (part of an internal structure called a client ID - process IDs and thread IDs are generated out of the same namespace, so they never overlap.)
- Threads sometimes have their own security context, or token, that is often used by multi-threaded server applications that impersonate the security context of the clients that they serve.

Threads cannot accidentally reference address space of another process unless the other process makes available part of its private address space as a shared memory section or one process has the right to open another process to user cross-memory functions.

## Virtual Memory

- Continuous pages in virtual memory do not match continuous pages in physical memory.
- Because most systems have less physical memory than the total virtual memory in use by running processes, the memory manager (page) some memory contents to disk. When it is needed again, the information is loaded back into memory.
- 32 bi x86 system's total virtual address space has a theoretical maximum of 4 GB.
- The upper half of memory by default in protected operating system memory
- The bottom half is for other processes.
- This can be changed with the increaseuserva to expand User process space max to 3GB and 1GB to system space
- 64 bit windows address space: 7152 GB on IA-64 systems and 8192 GB on x64 systems.

## Kernel Mode vs User Mode

User application code runs in usermode. Operating system code runs in kernel mode.

Kernel mode: mode of execution in a processor that grants access to all system memory and all CPU instructions. This is used so that misbehabing apps can't disrupt the stability of the system as a whole.

- All pages in virtual memory tagged to indicate what access mode the processor must be into read/write the page
  - Kernel mode
  - User mode 
  - Read-only

32-bit Windows provides no protection to private read/write system memory being used by components running in kernel mode. Example: OS and device driver has complete access to system space memory and can bypass Windows security.

64-bit Windows, the KCMS (Kernel Mode Code Signing) dictates that any 64-bit device drivers must be signed with a a cryptographic key assigned by a major code certification authority. The user cannot explicitly force the installation of unsigned drivers. There is a one-time exception that requires the restriction to be disabled manually at boot time. A user application switchs from user-mode to kernel-mode when it makes a system services call.

## Terminal Services and Multiple Sessions

Terminal Services: the support in Windows for multiple interactive user sessions on a single system.

The first session is considered the services session, or session zero, and contains system service hosting processes. The first login session at the physical console of the machine is session one. Additional sessions are created through remote desktop, or fast user switching. Client editions permit a single remote user. If someone is logged on locally that session is locked.

Server editions support 2 remote connections but can have mor if licensed and configured as a terminal server.

## Objects and Handles

Kernel Object: a single, run-time instance of a statically defined object type.

Object type comprises:
- system-defined data type
- functions that operate on instances of the data type
- a set of object attributes

Object attribute: a field of data in an object that partially defines the object's state.
- Difference between an object and an ordinary data structure is that the internal structure of an object is opaque.
- Objects, through the help of the object manager, provides a convenient way for accomplishing:
  - Providing human-readable names for system resources
  - Sharing resources and data among processes
  - Protecting resources from unauthorized access
  - Reference tracking, which allows the system to know when an object is no longer in use so that it can automatically be deallocated. 

## Security

Core security capabilities of Windows:
- discretionary (need-to-know) and mandatory integrity protection for all shareable system objects
- security auditing
- user authentication at logon
- prevention of one user from accessing uninitialized resources that another user has deallocated

Forms of access control over objects:
- DAC (discretionary access control)
- Privileged access control. A method for ensuring someone can get to protected objects if the owner isn't available
- Mandatory integrity control. Required to protect objects that are being accessed from within the same user account

## Registry

Registry is:
- system database that contains information required to host and configure the system
- systemwide software settings that control the operation of Windows
- security database
- per-user configuration files
- window into in-memory volatile data

## Unicode

Windows stores most internal text strings as 16-bit wide Unicode characters.

## Kernel Debugging

Kernel debugging: examining internal kernel data structures and/or stepping through functions in the kernel.

Symbol files: contain names of function, and variables and the layout and format of data structures.

- Symbol files are generated by the linker
- They are not usually stored in the binary image to make them smaller and faster

To be able to use any kernel debugging tools to examine Windows kernel data structures, the correct symbol files are required
for at least the kernel image. These can be obtained for debugging use is to use the Microsoft on-demand symbol server by using
a special syntax for the symbol path that you specify in the debugger.

## Debugging tools for Windows

The Debugging Tools for Windows is included as part of the Windows SDK. They can be used to debug user-mode processes as well as the kernel. The debugging tools can be used to attach to a user-mode process and examine and/or change process memory. Two options are available when attaching.
- Invasive : The DebugActiveProcess function is used to establish a connection between the debugger and debugee. Allows for
examining/changing process memory, setting breakpoints, and performing other debugging functions.
- Noninvasive: The debugger opens the process with the OpenProcess function and does not attach to the process as a debugger.
Allows for examining/changing memory.

Two debuggers can be used for kernel debugging:
- command-line (kd.exe)
- GUI (Windbg.exe)

There are three types of kernel debugging that can be done with these tools:
- Open crash dump files as a result of a Windows system crash
- Connect to a live, running system and examine the system state.
- Connect to the local system and examine the system states. Called local kernel debugging.

Once connected, the debugger extension commands can be used to display the contents of internal data structures. The LiveKD tool can be used to use the standard Microsoft debuggers to examine a running system without running it in debugging mode. 
