---
layout: post
title: Low Level Software
date:   2014-08-13 00:00:00
---

## Modules

The largest building block of a program is a module. There are 2 types:

- Static libraries: Static libraries make up a group of source-code files that are built together
and represent a certain component of a program
- Dynamic Libraries: (Dynamic Link Libraries) are similar to static libraries, except they are not
embedded into the program and remain in a separate file.

## Common Code Constructs

Two basic code-level constructs

- Procedures: A procedure is a piece of ode that can be invoked by other areas in the program
- Objects: Used in OOD (object-oriented design) an object is a program component that has both data
and code associated with it.

## Registers

- Registers are faster than RAM
- When a register is simply used to allow instructions access to specific values it is only used for
transferring a value from memory to the instruction or the other way around.
- In other cases, the same register is used repeatedly and updated through a single funciton. This is
evidence the register is being used to store a local variable.

### Register Uses

- EAX/EBX/EDX: Generic register
- ECX: Generic register, sometimes used as a counter by repetitive functions
- ESI/EDI: Generic register, frequently used as a source/ destination pointer in instructions that copy memory
- EBP: Stack base pointer
- ESP: Stack pointer

## The Stack

The stack is an area in program memory that is used for short-term storage of information by the CPU and
the program.

- The stack grows down memory addresses
- The stack can be used for
  - Temporarily saved register values
  - Local variables
  - Function parameters and return addresses
  
## Heaps

A heap is a managed memory region that allows for the dynamic allocation of variable-sized blocks of memory
in runtime.

## Basic Instructions

### Moving Data

- 'MOV Dest, Src' : Moves data from register or memory

### Arithmetic

- 'ADD operand1, operand2' : Adds two signed or unsigned integers. The result is typically stored in
operand1 
- 'SUB operand1, operand2' : Subtracts the value at operand2 from the value of operand1. The result is typically
stored in operand1. This instruction works for both signed and unsigned integers.
- 'MUL operand' : Multiplies the unsigned operand by EAX and stores the result in a 64-bit value in EDX: EAX. (EDX:EAX means that the lower (least significant 32 bit are stored in EAX and the high (most significant) 32 bits are stored
in EDX. 
- 'DIV operand' : Divides the unsigned 64-bit value stored in EDX:EAX by the unsiged operand. Stores the quotient in EAX and the remainder in EDX.
- 'IMUL operand' : Multiplies the signed operand by EAX and stores the result in a 64-bit value in EDX:EAX.
- 'IDIV operand' : Divides the signed 64-bit value in EDX:EAX by the signed operand. Stores the quotient in EAX and
the remainder in EDX.

### Comparing Operands

- 'CMP operand1, operand2' : Simply subtracts the 2 values and sets all the relevant flags in EFLAGS.

### Conditional Branches

- 'JCC TargetCodeAddress' : Jumps if a certain flag is/isn't set.

### Function Calls

- 'CALL TargetAddress' : Calls a function by pushing the current instruction pointer onto the stack and jumping to the specified address.
- 'RET' : Returns from a function by popping the instruction pointer pushed to the stack by CALL and the resumes
execution from that address

## Compilers and Compilation

A compiler consists of:

- Front-end : The first part of a compiler that verifies if code is valid and converts it to the compiler's intermediate representation.
- Optimizer : The two primary goals for optimizers are usually either gathering the most high-performance code possible 
or generating the smallest program binaries. Optimizers can improve a program in:
  - Code Structure: This means potentially unrolling loops or determining the most efficient approach in a switch statement.
  - Redundancy Elimination: Removes redundant calculations or assigning values to variables that are never used.
- Back-end: Sometimes called the code generator, is responsible for generating target-specific code from the
intermediate code. The 3 most important stages that take place during the code generation process:
  - Instruction selection
  - Register allocation
  - Instruction scheduling
