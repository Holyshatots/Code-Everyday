// compiled with
//  gcc -I/usr/local/include/taglib -o libtagtest libtagtest.c -L/usr/local/lib -ltag_c

#include <stdio.h>
#include <stdlib.h>

#include <tag_c.h>

int main(int argc, char *argv[]){
	TagLib_File *file;
	TagLib_Tag *tag;
	const TagLib_AudioProperties *properties;
	
	file = taglib_file_new(argv[1]);
	if(file == NULL){
		printf("Could not read file");
		return 1;
	}
	
	tag = taglib_file_tag(file);
    properties = taglib_file_audioproperties(file);
    
	taglib_tag_set_title(tag, "songname"); //Set title
	printf("After set_title\n");
	//taglib_tag_set_artist(tag, "artist"); // Set artist
	printf("After set_artist\n");
		
	if(!taglib_file_save(file)){
		fprintf(stderr, "Error saving taglibfile\n");
	}
	
    taglib_tag_free_strings();
    taglib_file_free(file);
}
