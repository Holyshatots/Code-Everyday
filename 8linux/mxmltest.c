/*
 * Mitch McAffee
 * 
 * A test to learn how to use mxml library
 * 
 * compile with:
 * gcc -o mxmltest mxmltest.c -lmxml
 * 
 * NOTE: had to run 'sudo ldconfig' because of a missing libmxml.so.1
 * error
*/

#include <stdio.h>
#include <mxml.h>

/*
mxml_type_t type_int(mxml_node_t *node) {
	return (MXML_INT);
}
*/

int main(int argc, char *argv[]){
	FILE *fp;
	// int fd;
	char buffer[8192];
	char *ptr;
	int id;
	mxml_node_t *tree, *node;
	mxml_type_t *nodetype;
	
	  static const char	*types[] =	/* Strings for node types */
			{
			  "MXML_ELEMENT",
			  "MXML_INTEGER",
			  "MXML_OPAQUE",
			  "MXML_REAL",
			  "MXML_TEXT"
			};
	
	fp = fopen("idexample.xml", "r");
	tree = mxmlLoadFile(NULL , fp, MXML_INTEGER_CALLBACK);
	fclose(fp);
	if (!tree) {
		fputs("Unable to read XML file!\n", stderr);
		return (1);
	}
	printf("Ok.\n");
	node = mxmlFindPath(tree, "*/id");
	if(!node){
		printf("Could not find id\n");
	}
	if (node->type != MXML_TEXT){
		printf("Not of type text\n");
	}
	
	
	//id = mxmlGetInteger(node);
	//printf("id: %d\n", id);
	/*
	if(id==0){
		printf("Failed.\n");
	} else {
		printf("id: %d\n", id);
	}
	*/
	mxmlDelete(tree);
	return 0;
}
