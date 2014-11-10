// PIE, NX, statically linked, with symbols.
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <sys/mman.h>
#include <sys/stat.h>

#define MAPLEN (4096*10) //

void randop() {
	munmap((void*)0x0F000000, MAPLEN);     
// Makes sure the address at 0x0F000000 is unmappe
	void *buf = mmap((void*)0x0F000000, MAPLEN, PROT_READ|PROT_WRITE, MAP_ANON|MAP_PRIVATE|MAP_FIXED, 0, 0);					
// Maps memory at 0x0F000000 with RW access
	unsigned seed;
	if(read(0, &seed, 4) != 4) return;     
// Read 4 bytes from stdin for the seed
	srand(seed);					       
// Seed random
	for(int i = 0; i < MAPLEN - 4; i+=3) {
		*(int *)&((char*)buf)[i] = rand(); i
// Write random bytes to the buf
		if(i%66 == 0) ((char*)buf)[i] = 0xc3; 
// If a multiple of 66, the char at i
	}
	mprotect(buf, MAPLEN, PROT_READ|PROT_EXEC); 
// Make the memory readable and executeable
	puts("ROP time!");
	fflush(stdout);
	size_t x, count = 0;
	do x = read(0, ((char*)&seed)+count, 555-count); 
// Read from stdin to seed[count] with size 555-count
	while(x > 0 && (count += x) < 555 && ((char*)&seed)[count-1] != '\n');
	// x equals the number of bytes read or 555-count
	// conditions of while:
	// x > 0
	// (count += x) < 555
	// seed[count-1] != '\n'
}
// INPUT:
// Junk
// "A"*36+"B"*4
int main(int argc, char *argv[]) {
	struct stat st;
	if(argc != 2 || chdir(argv[1]) != 0 || stat("./flag", &st) != 0) {
		puts("oops, problem set up wrong D:");
		fflush(stdout);
		return 1;
	} else {
		puts("yo, what's up?");
		alarm(30); sleep(1);
		randop();
		fflush(stdout);
		return 0;
	}
}
