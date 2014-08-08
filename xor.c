/*
 * Mitch McAffee
 * -----------------------
 * Just XORs the two inputs.. Might be useful as encryption program
 * or something. IDK
*/

#include <stdio.h>

#define MAX_BUF 200

int main(int argc, char *argv[]){
	char string[MAX_BUF], key[MAX_BUF];
	int i;
	if (argc != 3){
		printf("Usage: ./xor string key \n");
		return 0;
	}
	for (i = 0;i < MAX_BUF - 1;i++){
		string[i] = string[i]^key[i];
	}
	printf("%s\n", &string[0]);
	return 0;
}
q
