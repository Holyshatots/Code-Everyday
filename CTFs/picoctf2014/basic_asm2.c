#include <stdio.h>

void main()
{
	asm(
	"mov $10357,%ebx\n\t"
	"mov $10487,%eax\n\t"
	"mov $8408,%ecx\n\t"
	"cmp %eax,%ebx\n\t"
	"imul %eax,%ebx\n\t"
	"sub %eax,%ebx\n\t"
	"mov %ebx,%eax\n\t"
	"add %ecx,%eax"	
	);
	// eax == 0x067948c4
}
