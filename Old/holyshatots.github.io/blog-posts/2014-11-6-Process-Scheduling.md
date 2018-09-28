---
layout: post
title: Process Scheduling
date:   2014-11-06 00:00:00
---

Process scheduler decides which process runs, when, and for how long.

### Multitasking

A multitasking operating system is one that can simultaneously interleave 
execution of more than one process. Multitasking OSes com in two flavors:

- __Preemptive multitasking__ : the scheduler decides when a process is to
cease running and a new process is to begin running. This includes Linux and most
modern operating systems.
- __Cooperative multitasking__ : a process does not stop running until it voluntarily
decides to do so.

### Linux Processor Scheduler

The first process scheduler was very simple but didn't scale. Then 0(1) scheduler
was made that scales well but doesn't perform well with interactive but doesn't
perform well with interactive applications. This was replaced by the modern
Completely Fair Scheduler (CFS).

### Policy

Policy is the behavior of the scheduler that determines what runs when.

__ I/O - Bound versus Processor Bound Processes__

- __I/O-Bound__ : a process that spends much of its time submitting and waiting
on I/O requests. Most GUI processes are I/O - bound.
- __Processor-bound__ : a process that spends much of their time executing code. An
example are programs that run on an infinite loop.

A process can exhibit both behaviors simultaneously.

### Process Priority

A scheduling algorithm called priority-based scheduling. The goal of this is to
rank processes based on their worth and need for processor time. The Linux kernel
implements two separate priority ranges:

- A "nice" value : a number from -20 to +19 with a default of 0-Larger ncie values
correspond to a lower priority.
- Real-time priority: By default they range from 0 to 99 and a higher real-time
priority values correspond to a greater priority.

### Timeslice

The __timeslice__ is the numeric value that represents how long a task can
run until it is preempted. In CFS, processes are assigned a proportion of the
processor based on the process's "nice" value. 

### Scheduler Classes

The Linux scheduler is modular, enabling different algorithms to scheduler different
types of processes. This modularity is called scheduler classes.
