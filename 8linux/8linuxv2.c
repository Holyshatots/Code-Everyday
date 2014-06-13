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
#include <fcntl.h>
#include <errno.h>

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
	size_t i;
	char *text;
	
	int id;
	int playToken;
	
	FILE *in_file;
	
	json_t *root;
	json_error_t error;
	
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
	
	json_t *data, *mix, *jsonid;
	// json_int_t id;
	
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
	/*
	
	if(file_exist("~/.8linux")) {
		// Load previously created token
		in_file = fopen("~/.8linux", "r");
		fgets(buf, 20, in_file);
		playToken = atoi(buf);
		fclose(in_file);
	} else {
		// Get token
		text = request("http://8tracks.com/sets/new.xml");
		root = json_loads(text, 0, &error);
		free(text);
		
	}
	printf("Play token: %d\n", playToken);
	*/
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
