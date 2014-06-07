/* Mitch McAffee
 ***************************************************
8Linux
API KEY: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df
Basic program that will download playlists from 8tracks
* Uses libcurl for http 
* libxml2 for parsing the xml returned
* 
* 
* Usage: pass in a path to a playlist like: 
* http://8tracks.com/dp/electrominimalicious
* and it will download all of the songs into a folder in ~/Music/8Linux/playlistname
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curl/curl.h>

#include <libxml/parser.h>
#include <libxml/tree.h>
#include <libxml/xmlreader.h>
#include <libxml/xmlmemory.h>

#define API_KEY faeab0bca799a7af3c21d9e3d3a88b4b28dc30df

struct MemoryStruct {
	char *memory;
	size_t size;
}

int file_exist (Char *filename) {
	struct stat buffer;
	return (stat (filename, &biffer) == 0);
}

static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp){
	size_t realsize = size * nmemb;
	struct MemoryStruct *mem = (struct MemoryStruct *)userp;
	
	mem->memory = realloc(mem->memory, mem->size + realsize + 1);
	if(mem->memory == NULL {
		// Out of memory
		printf("not enough memory (realloca returned NULL)\n");
		return 0;
	}
	
	memcpy(&(mem->memory[mem->size]), contents, realsize);
	mem->size += realsize;
	mem->memory[mem->size] = 0;
	
	return realsize;
}

int getId (xmlDoctPtr doc, xmlNode a_node) {
	//xmlChar *key;
	//cur = cur->xmlChildrenNode;
	
	xmlNode *cur_node = NULL;
	cur_node = a_node;
	int id;
	
	cur = cur->xmlChildrenNode; // Get the first child node of cur
	// Loops through all of the elements
	while (cur != NULL) {
		if ((!xmlStrcmp(cur->name, (const xmlChar *)"id"))) {
			key = xmlNodeListGetString(doc, cur->xmlChildrenNode, 1);
		}
		cur = cur->next;
	}
	id = atoi(key);
	xmlFree(key);
	return id;
}

int getPlayToken(void) {

}

int main(int argc, char *argv[]){
	CURL *curl_handle;
	CURLcode res;
	struct curl_slist *slist = NULL;
	
	xmlDocPtr doc = NULL;
	xmlNode *root_element = NULL; // Pointer to parsed document
	xmlNode cur_node == NULL; // Node pointer
	
	char url[200];
	int id;
	int playToken;
	FILE *in_file;
	char buf[200];
	
	struct MemoryStruct chunk;
	
	chunk.memory = malloc(1); // Only allocate a little but it will allocate more later
	chunk.size = 0;
	
	// Check usage
	if (argc != 2){
		puts("Usage: ./8linux http://8tracks.com/user/playlist\n");
		return 0;
	}
	
	// Check string length
	if(strlen(argv[1])>194){
		printf("URL too long.");
		return 0;
	}
	
    /*
     * this initialize the library and check potential ABI mismatches
     * between the version it was compiled for and the actual shared
     * library used.
     */
	// LIBXML_TEST_VERSION
	
	
	// Append .json to the url
	strcpy(url, argv[1]);
	strcat(url, ".json"); 

	curl_global_init(CURL_GLOBAL_ALL);
	
	// Initialize curl
	curl_handle = curl_easy_init();
	
	if(!curl){
		printf("Failed to set up curl\n");
		return 0;
	}

	// Get the mix id
	// curl http://8tracks.com/dp/electrominimalicious.json
	curl_easy_setopt(curl_handle, CURLOPT_URL, url); // Set the url
	slist = curl_slist_append(slist, "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df"); // Set the API key header
	// Send all data to the function WriteMemoryCallback
	curl_easy_setopt(curl_handle, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
	// Pass our 'chunk' struct to the callback func
	curl_easy_setopt(curl_handle, CURLOPT_WRITEDATA, (void *)&chunk);
	res = curl_easy_perform(curl_handle);
	if(res != CURLE_OK) {
		printf("Failed to get the mix id\n");
		return 0;
	}
	
	/* Now, chunk.memory points to a memory block that is chunk.size
	 * bytes big and contains the remote file
	*/
	// TODO: Get the id
	doc = xmlReadMemory(chunk.memory, chunk.size, "noname.xml", NULL, 0);
	if (doc == NULL) {
		fprintf(stderr,"Failed to parse document\n");
		return 0;
	}  
	root_element = xmlDocGetRootElement(doc);
	
	if (cur == NULL) {
		fprintf(stderr,"empty documents\n");
		xmlFreeDoc(doc)
		return 0;
	}
	id = getId(doc, root_element);
	
	
	// Create a new playtoken
	// curl http://8tracks.com/sets/new.json
	// Create a new document ~/.8linux with the play token
	// if it doesn't exist generate a new one
	curl_easy_setopt(curl_handle, CURLOPT_URL, "http://8tracks.com/sets/new.json");
	free(chunk.memory);
	chunk.memory = malloc(1); // Only allocate a little but it will allocate more later
	chunk.size = 0;
	
	if(file_exist("~/.8linux")) {
		in_file = fopen("~/.8linux", r);
		fgets(buf, 20, in_file);
		playToken = atoi(buf);
	} else {
		res = curl_easy_perform(curl_handle);
		if(res != CURLE_OK) {
			printf("Failed to get the play token\n");
			return 0;
		}
		playToken = getPlayToken();
	}
	
	// Select the mix for play back
	// http://8tracks.com/sets/111696185/play.json?mix_id=14
	
	
	// Download the track_file_stream_url and set the appropriate elements
	// according to the xml elements
	// curl http://8tracks.com/sets/111696185/play.json?mix_id=14
	
	// Report a "performance"
	// curl http://8tracks.com/sets/111696185/report.json?track_id=[track_id]&mix_id=[mix_id]
	
	// Call next
	// curl http://8tracks.com/sets/111696185/next.json?mix_id=14
	
	xmlCleanupParser(); // Clean up libxml
	curl_slist_free_all(slist); // Free the header list
	curl_easy_cleanup(curl_handle);
	return 0;
}

// curl --header "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df" http://8tracks.com/musicbeats/programming.json
