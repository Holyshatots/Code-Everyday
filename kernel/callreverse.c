/*
 * Mitch McAffee
 ************************************
 * Calls uses the kernel reverse.c
 * by opening /dev/reverse
*/

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){
	if (argc != 2){
		puts("Usage: ./callreverse 'line to pass in'");
		return 0;
	}
	
	int fd = open("/dev/reverse", O_RDWR); // Open the module
	if(fd == NULL){
		printf("Could not open /dev/reverse");
		return 1;
	}
	write(fd, argv[1], strlen(argv[1]));
	read(fd, argv[1], strlen(argv[1]));
	printf("Read: %s\n", argv[1]);
	return 0;
}

