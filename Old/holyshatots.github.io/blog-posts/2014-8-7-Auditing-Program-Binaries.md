---
layout: post
title: Program Vulnerabilities
date:   2014-08-07 00:00:00
---

**Security:** Having control of the flow of information on a system.

##What is a program vulnerability?

A vulnerability is a bug in a software program that allows for execution in a way not originally intended by
the original programmers. An exploit is a piece of code that leverages a vulnerability to leak information or 
even worse run malicious code. If an attacker is able to gain remote code execution on a computer, they will have 
the same level of access as the process that was exploited. So if the program was only running in user-mode,
anything malicious will be restricted to only certain low-risk functions. However, if the process is running
in administrator or root mode, the malicious code will have full access and rights to anything on the computer. This leads
to a full compromise of a machine. Just running code in user-mode is not fully sufficient as there are many 
privilege escalation vulnerabilities that allow the code to bypass restrictions and run with full privileges. 

## Types of vulnerabilities

### Stack Overflows

![Stack](/assets/article_images/2014-8-7-Auditing-Program-Binaries/StackFrameAnatomy.png)

Most popular type of program vulnerability. This is when input to the program overflows a buffer and is able
to overwrite other areas of the [stack](https://en.wikipedia.org/wiki/Stack-based_memory_allocation). To exploit this, most of the time the return address is overwritten
to a different value where the shell code is. The memory where the shell code is has to be marked as executable.

### Heap Overflows

	The heap is usually organized in [linked-lists](http://en.wikipedia.org/wiki/Linked_list). 
To exploit, overwrite a buffer that goes onto the heap and overwrite the next and previous addresses to
overwrite these members so that it is possible to write an arbitrary value into an arbitrary address in memory.
Overwrite is not immediate, the overwritten item must be freed first before it can take effect.
This vulnerability is not as common as stack overflows.

### String Filters

If the string is internally converted into Unicode ( such as in most strings that are in Win32) the attacker
can feed the program a sequence of ASCII characters that become workable shell code once converted into Unicode.

### Integer Overflows

Often leads to stack overflows because of a programmer error of not realizing the exact integer type. This is
common with the difference between unsigned and a signed integer.

### Type Conversion Errors

- Happens when incoming data types are mishandled and perform incorrect inversions on them.

Example: [IIS Indexing Service Vulnerabilityy](https://technet.microsoft.com/en-us/library/security/ms06-053.aspx)

## Vulnerability Prevention and Protection Bypasses

### Non-executable (NOEXEC) Memory

- Pages are marked and executable marked pages cannot run code
- To bypass, called __return-to-libc__, modify the functions return address to point to a well-known function
(such as a runtime library or a system API) that helps attackers gain control over the process.


If you are interested in reversing / exploitation I suggest Myne-US's [From 0x90 to 0x4c454554, a journey into
exploitation](http://myne-us.blogspot.se/2010/08/from-0x90-to-0x4c454554-journey-into.html).
