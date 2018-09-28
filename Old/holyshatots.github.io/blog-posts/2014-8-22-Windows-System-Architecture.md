---
layout: post
title: Windows System Architecture
date:   2014-08-22 00:00:00
image: /assets/article_images/2014-8-22-Windows-System-Architecture/bill_gates.jpg
---

### Operating System Model

- Operating system kernel code runs in kernel mode.
- Application code runs in user mode.
- Windows has the bulk of the operating system and device driver code shar ethe same kernel-mode protected memory space. This means, potentially, any OS component or device driver can corrupt data being used by other components.
  - Windows implements some kernel protection mechanisms including, PatchGuard and Kernel Mode Code Signing.
- Kernel-mode components of Windows embody basic object-oriented design principles.
- Most of Windows is written in C so it cannot follow being an object-oriented system strictly.

### Architecture Overview

- Four basic types of user-mode processes:
  - Fixed (or hardwired) __system support services__, such as the logon process and the Session manager, that are not Windows services.
  - __Services processes__ that host Windows services, such as Task Scheduler and Print Spooler.
  -__User applications__
  -__Environment subsystem server processes__, which implement part of the support for the operating system environment, or personality, presented to the user or programmer.
- User applications don't call native Windows OS services directly; They go through one or more subsystem DLLs.

Kernel-mode components of Windows include:
- The Windows __executive__ contains the base operating system services. Such as memory management, process and thread management, security, I/O, networking, and interprocess communication.
- The Windows __kernel__ consists of low-level operating system functions, such as thread scheduling, interrupt and exception dispatching, and multiprocessor synchronization. Also provides a set of routines and basic objects that the rest of the executive uses to implement higher-level constructs.
-__Device drivers__ include both hardware device drivers, which translate user I/O function calls into specific hardware device I/O requests, as well as non-hardware device drivers such as file system and network drivers.
- __The hardware abstraction layer (HAL)__ is a layer of code that isolates the kernel, the device drivers, and the rest of the Windows executive from platform-specific hardware differences.
- The __Windowing and graphic system__ implements the graphical user interface functions (GDI and Windows functions).

Core Windows System Files:
-Ntoskrnl.exe : Executive and kernel
-Ntkrnlpla.exe (32-bit only) : Executive and kernel, with support for PAE (physical address extension), which allows 32-bit systems to address up to 64 GB of physical memory and to mark memory as nonexecuteable.
- Hal.dll : Hardware abstraction layer
- Win32K.sys : Kernel-mode part of the Windows subsystem
- Ntldll.dll : Internal support function and system service dispatch stubs to executive functions.
- Kernel32.dll, Advapi32.dll, User32.dll, Hdi32.dll : Core Windows subsystem DLLs

### Portability
- Windows acheives portability through a layered design. Low-level portions of the system are processor-architecture-specific are isolated into modules so that upper layers are shielded from differences. The two key components of OS portability are the kernel and the hardware abstraction layer (HAL).
- Windows is mostly written in C iwth some C++. Assembly language is only used for parts that need to communicate directly with system hardware or extremely performance-sensitive parts.

### Symmetric Multiprocessing

Windows uses symmetric multiprocessing (SMP).
- This means there is not a master processor - the operating system and user threaeds can be scheduled to run on any processor.
- All processors share just one memory space

This is in contrast with asymmetric multiprocessing (ASMP).
- In ASMP the OS selects one processor to execute operating system code while the others run only user code.

Windows supports three modern types of multiprocessor systems:
- Hyper-threading : provides two lgoical processors for each physical core.
- NUMA (non-uniform memory architecture) : processors are grouped in smaller units called nodes. Each node has its own processors and memory is connected to the longer system through a cache-coherent interconnect bus.
- Multi-core: Multiple physical cores.

### Scalability

Windows implements the following to be successful as a multiprocessor system:
- The ability to run operating system code on any available processor and on multiple processors at the same time.
- Multiple threads of execution within a single process, each of which can execute simultaneously on different processors. 
- Fine-grained synchronization within the kernel as well as device drivers and server process.
- Programming mechanisms such as I/O completion parts that facilitate the efficient implementation of multi-threaded server processes that can scale well on multiprocessor systems.

## Key System Components

### Environment Subsystems and Subsystem DLLs
- The role of an environment subsystem is to expose some subset of the base Windows executive system services to application programs. Each subsystem can provide access to different subsets of the native services in Windows.
- User applications don't call Windows system services directly, they go through on or more subsystem DLLs.

When an application calls a function in a subsystem DLL, one of three things can occur:
1. The function is entirely implemented in user mode inside the subsystem DLL.
2. The function requires one or more calls to the Windows executive.
3. The function requires some work to be done in the environment subsystem process.

Note: Some functions can be a combination of the 2nd and 3rd option. I.E. CreateProcess and CreateThread functions.

### Subsystem startup

- Subsystems are started by the session manager (Smss.exe) process.
- The subsystem startup info is stored under the registery key HKML\SYSTEM\CurrentControlSet\Control\SesssionManager\Subsystems
- The required value lists the subsystems that load when the system boots
- Optional value indicates that the SUA subsystem will be started on demand
- The registry value knock contains the file name of the kernel-mode portion of the Windows subsystem, Win32k.sys.

### Windows Subsystem
The Windows subsystem consists of the following major components:
- For each session, an instance of the environment subsystem process (srss.exe) loads three DLLs (Basesrv.dll, Winsrv.dll, and Csrsrv.dll)
- A kernel-mode device driver (Win32k.sys) that contains
  - The window manager. Controls window displays; manages screen output; collects input form keyboard, mouse, and other devices, and pauses user messages to applications
  - The Graphics Device Interface (GDI) which is a library for graphics to output devices.
  - Wrappers for DirectXS support that is implemented in another kernel driver (Dxgkrnl.sys)
- The console host process (Conhost.exe), which provides support for console applications.
- Subsystem DLLs that translate documented Windows API functions into the apporpriate and mostly undocumetned kernel-mode system service calls in Ntoskrnl.exe and Win32k.sys
- Graphics device drivers for hardware-dependent graphics display drivers, printer drivers, and video minport drivers. 

### Subsystem for Unix-based applications

The SUA (subsystem for Unix-based applications) enables compiling and running custom UNIX-based applications on a computer running windows Server or the Enterprise or Ultimate editions of the Windows Client.

### Ntdll.dll

Ntdll.dll is a special subsystem support library primarily for the use of the subsystem DLLs. Contains two types of functions:
- System service dispatch stubs to Windows executive system services
- Internal support functions used by subsystems, subsystem DLLs, and other native images.

The first group of functions provides the interface to the Windows executive system services that can be called from user mode.

### Executive

The Windows Executable to the upper layer of Ntoskrnl.exe. The kernel is the lower layer. The executive contains the following major components:
- The __configuration manager__ is responsible for implementing and managing the system registry.
- The __process manager__ creates and terminates processes and threads. 
- The __security reference monitor__ enforces security policies on the local computer.
- __I/O__ manager implements device-independent I/O and is responsible for dispatching to the appropriate device drivers for further processing.
- The __Plug and Play (PnP) manager__ determines which drivers are required to support a particular device and loads those drivers.
- The __power manager__ coordinates power events and generates power management I/O notifications to device drivers. When the system is idle, the power manager can be configured to reduce power consumption by individual devices are handled by device drivers but are coordinated by the power manager.
- The __Windows Drive Model Windows Management Instrumentation routes__ enable device drivers to pbulish performance and configuration information and receive commands from the user-mode WMI service.
- the __cache-manager__ improves the performance of the file-based I/O by causing recently referenced disk data to reside in main memory for quick access.
- The __memory manager__ implements virtual memory, a memory management scheme that provides a large private address space for each process that can exceed available physical memory. Also provides underlying support for the cache manager.
- The __logical prefetcher__ and __Superfetch__ accelerate system and process startup by optimizing the loading of data referenced during the startup of the system or a process. The executive contains four groups of support functions that are used by the components just listed
- The __object manager, which creates, manages, and deletes Windows executive objects and abstract data types that are used to represent operating system resources.
- The __Advanced LPC facility (ALPC)__ passes messages between a client process and a server process on the same computer
- A broad set of common run-time library functions , such as string processing, arithmetic operations, data type, conversion, and security structure processing.
- Executive support routines, such as system memory allocation, interlocked memory access, as well. As three special types of synchronization objects: resources, fast mutexes, and push-locks.

### Kernel

The kernel consists of a set of functions in Ntoskrnl.exe that provides fundamental mechanisms used by the executive components, as well as low-level hardware architecture-dependent support that is different on each processor architecture.

### Kernel Objects

The kernel implements a set of simple objects compared to outside of it. These help the kernel control central processing and support the creation of executive objects
__Control objects__: kernel object that establishes semantics for controlling various system functions.
__Dispatcher objects__: kernel objects that incorporate synchronization capabilities that alter thread scheduling.

### Kernel Processor Control Region (KPCR) and Control Blocks (KIRCB)

Processor control region: A data structure the kernel uses to store processor-specific data. Contains basic information such as the processor interrupt dispatch table (IDT) task-state segment (TSS), and global descriptor table (GPT).
- The KPCR also contains an embedded data structure called the kernel processor control block (KPRCB). The KPRCB is a private structure only used by the kernel code in Ntoskrnl.exe. It contains a lot of information about the processor.

### Hardware Support

A major job of the kernel is to abstract or isolate the executive and device drivers from variations between the hardware architectures. The kernel uses as much common code between architectures but there are some things that require architecture specific code. 

### Hardware Abstraction Layer

The HAL is a loadable kernel-mode module (Hal.ddl) that provides the low-level interface to the hardware platform on which Windows is running.

### Device Drivers

Device drivers are loadable kernel-mode modules that interface between the I/O manager and the relevant hardware. They run in kernel-mode in one of the 3 contexts:
- In the context of the user thread that initiated the I/O function
- in the context of a kernel-mode system thread
- As a result of an interrupt (and therefore not in the context of any particular process or thread).

Device drivers interact with hardware through HAL and are portable due to being written in C.
Types of device drivers:
- Hardware device drivers manipulate hardware to write output to or retrieve input form a phyiscal device or network.
- File system drivers are Windows drivers that accept file-oriented I/O requests and translate them into I/O requests bound for a particular device.
- File system fileter drivers, intercept I/Os, and perform some added value processing before passing the I/O to the next layer. 
- Network redirectors and servers are file system drivers that transmit I/O requests to a machine on the network and receive such requests, respectively.
- Protocol drivers implement a networking protocol such as TCP/IP, netBEUI, and IPX/SPX
- Kernel streaming filter drivers are chained together to perform signal processing together to perform signal processing on data streams, such as recording or displaying audio or video.

### Windows Driver Model (WDM)

From the WDM perspective there are three kinds of drivers:

- A bus driver services a bus controller, adapter bridge, or any device that has child devices. Each type of bus (PCI, PCMCIA, and USB) on a system has on bus driver. Required driver.
- A function driver is the main device driver and provides the operation interface for its device. It is  a required driver unless the device is used raw. A function driver is by definition the driver that knows most about a particular device, and it is usually the only driver that access device-specific registers.
- A filter driver is used to add functionality to a device (or existing driver) or to modify I/O requests or responses from other drivers. (Often used to fix hardware that provides incorrect information about its hardware resource requirements). Filter drivers are optional and can exist in any number, placed above or below a function driver and above a bus driver. 

I WDM, no single driver controls all aspects of a device.

### Windows Driver Foundation

The Windows Driver Foundation (WDF) simplifies Windows driver development by providing two frameworks: the kernel-mode driver framework (KMDF) and the user-mode driver framework (UMDF). KMDF provides a simpler interface to WDM. UMDF enables certain classes to run in user-mode. This way, if a UMDF driver crashes, the process dies and usually restarts without making the system unstable. Notes: A list of the installed drivers can be viewed with Msinfo32.

### System Processes

The system processes that run on every Windows system:
- Idle process (contains one thread per CPU to account for idle CPU time).
- System process : contains the majority of the kernel-mode system threads
- Session manager : Smss.exe
- Local session manager : lsm.exe
- Windows subsystem : Csrss.exe
- Session O initialization : Wininit.exe
- Logon process : Winlogon.exe
- Service control manager : Services.exe
- Local security authentication server : Lsass.exe

### System Idle Process

Processes are identified by their service name but this one goes by many names. This process isn't running a real user-mode image (there is no "SystemIdleProcess.exe").

### System Process and System Threads

The system process (process ID 4) runs a special type of thread hat runs only in kernel-mode: a kernel-mode system thread.
Windows and device drivers may create system threads during system initialization to perform operations that require thread context. Examples: Issuing and waiting for I/Os or other object or pooling a device. By default, system threads are owned by the system process, but can be created in any process.

### Session Manager (Smss)

First user-mode process created in the system. A session startup instance of Smss does the following:
1. Calls NtSetSystemInformation with a request to set up kernel-mode session data structures. This in turn calls the itnernal memory manager function MmSessionCreate, which sets up the session virtual address space that will contain the session paged pool and the per-session data structures allocated by the kernel-mode part of the Windows subsystem (Win32k.sys) and other sessions space device drivers.
2. Creates the subsystem process(es) for the session
3. Creates an instance of Winlogon or Wininit (for session 0)
4. Exits

### Windows Initialization Process (Wininit.exe)

Performs the following system intialization functions:
- Marks itself critical so that if it exits prematuerly and the system is booted in debugging mode, it will break into the debugger.
- Initializes the user-mode scheduling infrastructure
- Creates the %windir%\temp folder
- Creates a windows stations (winsta0) and two desktops (Winlogon and default) for processes to run on in session O.
- Creates services.exe
- Starts Lsasss.exe (Local Security Authentication Subsystem Manger)
- Starts Lsm.exe (Local Session Manager)
- Waits forever for system shutdown

### Service Control Manager (SCM)

- Services are defined in the registry under HKLM\SYSTEM\CurrentControlSet\Services
- Responsible for starting, stopping, and interacting with service processes.

### Local Session Manager (Lsm.exe)

The lsm manages the state of terminal server session on the local machine. 

### Winlogon, LogonUI, and Userinit

The windows logon process handles interactive user logon process handles interactive user logons/logoffs. Notified of a logon when the secure attention sequence (SAS) keystroke combo is entered.
- The default SAS is Ctrl+Alt+Delete because this keyboard sequence cannot be intercepted by a user-mode application.

The identification and authentication aspects of the logon process are implemented through DLLs are credential providers.

Once the user-name and password have been captured, they are sent to the local security authentication server process to be authenticated. Upon a successful authentication, LSASS calls a function in the security reference monitor to generate an access token object that contains the user's security profile.
- If using UAC and login in as a administrator, a second, restricted version of the token is used to create the initial processes in the user's session.

Userinit performs some initialization of the user environment and then looks in the register at the Shell value and creates a process to run the system-defined shell. It then exits Winlogon is active during logon, logoff as well as whenever it intercepts the SAS from the keyboard (Ctrl+Alt+Del).