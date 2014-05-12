/*
* Mitch McAffee 4/17/2014
* -----------------------------------------------------------------------
* Here's a simple help free challenge to get you started: write a program
* that takes a file as an argument and counts the total number of lines. 
* Lines are defined as ending with a newline character. Program usage 
* should be:
* count filename.txt
* and the output should be the line count.
*/

#include <stdio.h>

int main(int argc, char *argv[]){
	int count = 0;
	char a;
	
	if (argc != 2){
		puts("Usage: ./count filename.txt");
		return 0;
	}
	FILE *in_file = fopen(argv[1], "r");
	if(in_file == NULL){
		printf("Could not open %s.\n", argv[1]);
		return 1;
	}
	
	while((a = fgetc(in_file)) != EOF){
		if(a == '\n'){
		count++;
		}
	}
	printf("%d\n", count);
	fclose(in_file);
	return 0;
}
