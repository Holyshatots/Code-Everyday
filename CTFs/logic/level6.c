#include <stdio.h> 
#include <stdlib.h>
int main (int argc, char *argv[]) {
	char buf[512];
	if (argc < 2) { printf("%s\n","Failed"); return 1; }
	snprintf(buf, sizeof(buf), argv[1]);
	buf[sizeof (buf) - 1] = '\x00';
	return 0;
}
