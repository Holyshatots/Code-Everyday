/* Mitch McAffee
 * -------------------------------
 * salt= 7035
 * md5= 265150c29c44f0de9b3cf6970c5e18c6
*/

#include <stdio.h>
#include <crypt.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

// #DEFINE MD5 265150c29c44f0de9b3cf6970c5e18c6

static const char alphabet[] =
"abcdefghijklmnopqrstuvwxyz"
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"0123456789";
static const int alphabetSize = sizeof(alphabet) - 1;
char salt[] = "$1$7035";
char md5[] = "265150c29c44f0de9b3cf6970c5e18c6";

void check(char* pass){
	char *result;
	result = crypt(salt, pass);
	printf("%s", result);
	if(strcmp (result, md5)){
		printf("%s", pass);
	}
}

void bruteImpl(char* str, int index, int maxDepth)
{
    for(int i = 0; i < alphabetSize; ++i)
    {
        str[index] = alphabet[i];

        if (index == maxDepth - 1){
			//printf("%s\n", str);
			check(str);
		} else bruteImpl(str, index + 1, maxDepth);
    }
}

void bruteSequential(int maxLen)
{
    char* buf = malloc(maxLen + 1);

    for(int i = 1; i <= maxLen; ++i)
    {
        memset(buf, 0, maxLen + 1);
        bruteImpl(buf, 0, i);
    }

    free(buf);
}


int main( int argc, char *argv[]){
//	char salt[] = "$1$7035";
//	char md5[] = "265150c29c44f0de9b3cf6970c5e18c6";
//	char new;
//	char key;
	int len = 3;
	bruteSequential(len);
	// printf("%s", key);
	return 0;
}




