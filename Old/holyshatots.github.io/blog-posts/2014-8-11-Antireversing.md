---
layout: post
title: Antireversing Techniques
date:   2014-08-11 00:00:00
image: /images/44932297.jpg
--- 
A lot of these approaches will _NOT_ stop a dedicated reverser. Most of these defenses simply make reversing 
the binary take more time, not making it worth it to reverse engineers to even mess with.

## Basic Approaches to Antireversing

1. Eliminating Symbolic Information: Stripping all obvious textual information.
2. Obfuscating the program: Modifying the program so that it keeps it's functionality but is less readable.
3. Embedding Antidebugger Code: Intentionally perform actions that would damage or disable a debugger, if one
is attached.

## Eliminating Symbolic Information

Have a program go over the code before compiling and rename all internal names to meaningless strings.

### Debugger Basics:
 
When a breakpoint is set on an instruction, the instruction is replaced with an `int 3` instruction.
There are also __hardware breakpoints__ that are managed by the processor and are invisible to software.
Single-stepping is implemented on x86 by using the Trap Flag (TF).

### IsDebuggerPresent API:

A windows API used to detect user-mode debuggers (OllyDbg/WinDbg).
To detect debuggers the function checks the current processes's PEB (Process Environment Block).
IsDebuggerPresent is only 4 lines of assembly and can be put inline sneakily to detect the presence 
easily.

## Trap Flag

Enable the Trap-Flag and check if an exception is raised. If one is not raised, a debugger is attached. This is a 
simple technique that can be avoided using hardware breakpoints.

## Code Checksums

Check checksums of all of the code or just a section of it. Debuggers must modify the code to install
breakpoints.
Doesn't effect hardware-breakpoints.

### Techniques to disassemble binaries:

1. Linear Sweep: The disassembler goes through the .text section of the program and sequentially
decodes each byte.
2. Recursive Traversal: The jump and branch instructions are followed. This is less susceptible to mistakes than
the linear sweep.

[More info..]{http://resources.infosecinstitute.com/linear-sweep-vs-recursive-disassembling-algorithm/)

OllyDbg ... Recursive Traversal

SoftICE ... Linear Sweep

WinDbg ... Linear Sweep

IDA Pro ... Recursive Traversal

PEBrowse Professional ... Recursive Traversal

## Confusing Linear Sweep Disassemblers

Adding a junk byte and an unconditional jump before it will often confuse the disassembler and show
the wrong instructions or illegal instructions.

## Recursive Traversal Disassemblers

An opaque predicate (a false branch where it appears conditional but is not). The fake branch leads to junk data.



