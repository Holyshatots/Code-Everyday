#include <stdio.h>

int main(int argc, char *argv[])
{
	_asm_("
	
		MOV $10357,%ebx
		MOV $10487,%eax
		MOV $8408,%ecx
		CMP %eax,%ebx
		JL L1
		JMP L2
		L1:
		IMUL %eax,%ebx
		ADD %eax,%ebx
		MOV %ebx,%eax
		SUB %ecx,%eax
		JMP L3
		L2:
		IMUL %eax,%ebx
		SUB %eax,%ebx
		MOV %ebx,%eax
		ADD %ecx,%eax
		L3:
		NOP

	");
}