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
* compiled with: 
* `curl-config --cc --cflags` -o 8linuxv2 8linuxv2.c `curl-config --libs` -ljansson 

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>

#include <jansson.h>
#include <curl/curl.h>

#define BUFFER_SIZE  (256 * 1024) // 256KB

#define URL_SIZE     256

#define API_KEY faeab0bca799a7af3c21d9e3d3a88b4b28dc30df

struct write_result {
    char *data;
    int pos;
};

static size_t write_response(void *ptr, size_t size, size_t nmemb, void *stream) {
    struct write_result *result = (struct write_result *)stream;

    if(result->pos + size * nmemb >= BUFFER_SIZE - 1)
    {
        fprintf(stderr, "error: too small buffer\n");
        return 0;
    }

    memcpy(result->data + result->pos, ptr, size * nmemb);
    result->pos += size * nmemb;

    return size * nmemb;
}

static char *request(const char *url) {
    CURL *curl = NULL;
    CURLcode status;
    struct curl_slist *headers = NULL;
    char *data = NULL;
    long code;

    curl_global_init(CURL_GLOBAL_ALL);
    curl = curl_easy_init();
    if(!curl)
        goto error;

    data = malloc(BUFFER_SIZE);
    if(!data)
        goto error;

    struct write_result write_result = {
        .data = data,
        .pos = 0
    };

    curl_easy_setopt(curl, CURLOPT_URL, url);

    headers = curl_slist_append(headers, "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df");
    curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);

    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_response);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &write_result);

    status = curl_easy_perform(curl);
    if(status != 0)
    {
        fprintf(stderr, "error: unable to request data from %s:\n", url);
        fprintf(stderr, "%s\n", curl_easy_strerror(status));
        goto error;
    }

    curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &code);
    if(code != 200)
    {
        fprintf(stderr, "error: server responded with code %ld\n", code);
        goto error;
    }

    curl_easy_cleanup(curl);
    curl_slist_free_all(headers);
    curl_global_cleanup();

    /* zero-terminate the result */
    data[write_result.pos] = '\0';

    return data;

error:
    if(data)
        free(data);
    if(curl)
        curl_easy_cleanup(curl);
    if(headers)
        curl_slist_free_all(headers);
    curl_global_cleanup();
    return NULL;
}

int main(int argc, char *argv[]){
	char url[URL_SIZE];
	char *text;
	// char buf[80];
	const char *message;
	char *message2;
	char  *file = "/.8tracks";
	char *filename;
	
	filename = getenv("HOME");
	
	filename = strcat(filename, file);
	// printf("Play token file: %s\n", filename);
	int id;
	int playToken = 0;
	
	int in_file;
	FILE *in_file2;
	
	json_t *root;
	json_error_t error;
	json_t  *mix, *jsonid;
	json_t *jsonplayToken;
	
	// Check usage
	if (argc != 2){
		puts("Usage: ./8linux http://8tracks.com/user/playlist\n");
		return 0;
	}
	
	// Append .json to the url
	strcpy(url, argv[1]);
	strcat(url, ".json"); 
	
	// Get the mix id
	// curl http://8tracks.com/dp/electrominimalicious.json
	text = request(url);
	if(!text)
	return 1;

	// Set up json and free the text
	root = json_loads(text, 0, &error);
	free(text);
	
	if(!root) {
		fprintf(stderr, "error: on line %d: %s\n", error.line, error.text);
		return 1;
	}
	
	// Check the types of the json document	
	mix = json_object_get(root, "mix");
	if(!json_is_object(mix)){
		fprintf(stderr, "error: mix is not an object\n");
		json_decref(root);
		return 1;
	}
	jsonid = json_object_get(mix, "id");
	if(!json_is_integer(jsonid)) {
		fprintf(stderr, "error: id is not an integer\n");
		json_decref(root);
		return 1;
	}
	
	// Set the mix id
	id = json_integer_value(jsonid);
	printf("Mix ID: %d\n", id);
	
	// Free the document
	json_decref(root);
	
	// Create a new playtoken
	// curl http://8tracks.com/sets/new.json
	// Create a new document ~/.8linux with the play token
	// if it doesn't exist generate a new one
	
	in_file = open(filename, O_CREAT | O_WRONLY | O_EXCL, S_IRUSR | S_IWUSR);
	if (in_file < 0) {
		/* failure */
		if (errno == EEXIST) {
			/* the file already existed */
			// Load previously created token
			printf("~/.8linux exists.\n");
			in_file2 = fopen(filename, "r");
			fread(message2, 1, 9, in_file2);
			// printf("%s\n", message2);
			playToken = atoi(message2);
			fclose(in_file2);
		}
	} else {
		/* now you can use the file */
		// Get token
		if(close(in_file) < 0){
			fprintf(stderr, "error: failed to close ~/.8linux");
			return 1;
		}
		in_file2 = fopen(filename, "w");
		text = request("http://8tracks.com/sets/new.json");
		if(!text) {
			return 1;
		}	
		root = json_loads(text, 0, &error);
		if(!root) {
			fprintf(stderr, "error: on line %d: %s\n", error.line, error.text);
			return 1;
		}
		free(text);
		jsonplayToken = json_object_get(root, "play_token");
		if(!json_is_string(jsonplayToken)) {
			fprintf(stderr, "error: playToken is not a string\n");
			json_decref(root);
			return 1;
		}
		message = json_string_value(jsonplayToken);
		playToken = atoi(message);
		printf("Writing: %s\n", message);
		fwrite(message, 1, sizeof(message)+1 , in_file2);
		fclose(in_file2);
		json_decref(root);
		
	}	
	printf("Play token: %d\n", playToken);
	
	// Select the mix for play back
	// http://8tracks.com/sets/111696185/play.xml?mix_id=14
	
	
	// Download the track_file_stream_url and set the appropriate elements
	// according to the xml elements
	// curl http://8tracks.com/sets/111696185/play.xml?mix_id=14
	
	
	// Report a "performance"
	// curl http://8tracks.com/sets/111696185/report.xml?track_id=[track_id]&mix_id=[mix_id]
	
	
	// Call next
	// curl http://8tracks.com/sets/111696185/next.xml?mix_id=14
	
	return 0;
}

// curl --header "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df" http://8tracks.com/musicbeats/programming.json
// curl --header "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df" http://8tracks.com/sets/new.json
