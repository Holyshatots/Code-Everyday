/*
* Mitch McAffee 4/18/2014
* --------------------------
* In this challenge, given the name of a file, print out the size of 
* the file, in bytes. If no file is given, provide a help string to the 
* user that indicates how to use the program.
*/

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	int count;
	int fd;
	
	if (argc != 2){
		puts("Usage: ./size filename.txt");
		return 0;
	}
	FILE *in_file = fopen(argv[1], "r");
	if(in_file == NULL){
		printf("Could not open %s.\n", argv[1]);
		return 1;
	}
	fd = fileno(in_file);
	struct stat buf;
	fstat(fd, &buf);
	count = buf.st_size;
	printf("%d bytes\n ", count);
	return 0;
}
