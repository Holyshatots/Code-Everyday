global loader  		       ; the entry symbol for ELF

MAGIC_NUMBER equ 0x1BADB002    ; define the magic number constant
CHECKSUM     equ -MAGIC_NUMBER ; calculate the checksum

section .text:		       ; start of the text (code) section
align 4			       ; the code must be 4 byte aligned
    dd MAGIC_NUMBER	       ; write the magic number to the machine code
    dd CHECKSUM		       ; and the checksum

loader:			       ; the loader label
  mov eax, 0xCAFEBABE	       ; place the number 0xCAFEBABE in the reg eax
.loop:
  jmp .loop		       ; loop forever
