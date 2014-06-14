/* Mitch McAffee
 * themcaffee@gmail.com
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

struct track {
	json_t *jsontrackname, *jsonartist, *jsonstreamURL, *jsonid, *jsonat_beginning, *jsonat_last_track;
	const char *trackname;
	const char *artist;
	const char *streamURL;
	char filename[1000];
	int id;
	int at_beginning; //True if at the first track in the mix
	int at_last_track; //True if at the last track in the mix
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
	//curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, headers);
	
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

static size_t write_file(void *ptr, size_t size, size_t nmemb, void *stream) {
	int written = fwrite(ptr, size, nmemb, (FILE *)stream);
	return written;
}

int request_to_file(const char *url, const char *filename) {
	FILE *fp;
	CURL *curl_handle;
	CURLcode res;
	curl_global_init(CURL_GLOBAL_ALL);
	curl_handle = curl_easy_init();
	if(!curl_handle){
		fprintf(stderr, "Could not get curl handle\n");
		curl_global_cleanup();
		return 1;
	}
	curl_easy_setopt(curl_handle, CURLOPT_URL, url);
	curl_easy_setopt(curl_handle, CURLOPT_WRITEFUNCTION, write_file);
	curl_easy_setopt(curl_handle, CURLOPT_FOLLOWLOCATION, 1L);
	fp = fopen(filename, "wb");
	if (fp == NULL) {
		curl_easy_cleanup(curl_handle);
		fprintf(stderr, "Could not open %s\n", filename);
		return 2;
	}
	curl_easy_setopt(curl_handle, CURLOPT_WRITEDATA, fp);
	printf("Downloading: %s ...", filename);
	res = curl_easy_perform(curl_handle);
	if(res != CURLE_OK){
		fprintf(stderr, "curl failed: %s\n", curl_easy_strerror(res));
		return 3;
	}
	printf(" Done!\n");
	fclose(fp);
	curl_easy_cleanup(curl_handle);
	return 0;
}

int main(int argc, char *argv[]){
	char url[URL_SIZE];
	char *text;
	const char *message;
	char message2[100] = {0};
	char  *file = "/.8tracks";
	char *filename;
	char *home;
	const char *mixName;
	
	home = getenv("HOME"); // Have to do this because strcpy was giving me 
	filename = getenv("HOME"); // grief
	
	filename = strcat(filename, file);
	// printf("Play token file: %s\n", filename);
	
	char selectURL[1000] = {0};
	char selectURL2[1000] = {0};
	
	int id;
	int trackCount = 0;
	int playToken = 0;
	
	FILE *in_file2;
	
	json_t *root;
	json_error_t error;
	json_t  *mix, *jsonid, *jsontrackCount, *jsonmixName;
	json_t *jsonplayToken;
	json_t *jsonset, *jsontrack;
	
	int first = 1;
	int at_last_track;
	
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
	jsontrackCount = json_object_get(mix, "tracks_count");
	if(!json_is_integer(jsontrackCount)) {
		fprintf(stderr, "error: trackCount is not an integer\n");
		json_decref(root);
		return 1;
	}
	trackCount = json_integer_value(jsontrackCount);
	if(trackCount == 0){
		fprintf(stderr, "error: unable to get track count");
	}
	jsonmixName = json_object_get(mix, "name");
	if(!json_is_string(jsonmixName)) {
		fprintf(stderr, "error: mixName is not a string\n");
		json_decref(root);
		return 1;
	}
	mixName = json_string_value(jsonmixName);
	printf("Mix name: %s\n", mixName);
	// Set the mix id
	id = json_integer_value(jsonid);
	printf("Mix ID: %d\n", id);
	printf("Number of tracks: %d\n", trackCount);
	// Free the document
	json_decref(root);
	
	// Create a new playtoken
	// curl http://8tracks.com/sets/new.json
	// Create a new document ~/.8linux with the play token
	// if it doesn't exist generate a new one
	
	in_file2 = fopen(filename, "r");
	if (in_file2 == NULL) {
		/* now you can use the file */
		// Get token
		printf("~/.8linux doesn't exist.\n");
		in_file2 = fopen(filename, "w");
		if(in_file2 == NULL){
			printf("Could not create ~/.8linux");
		}
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
	} else {
		/* the file already existed */
		// Load previously created token
		printf("~/.8linux exists.\n");
		fread(message2, sizeof(int), 15, in_file2);
		playToken = atoi(message2);
		fclose(in_file2);
	}	
	printf("Play token: %d\n", playToken);
	snprintf(selectURL, 999, "http://8tracks.com/sets/%d/play.json?mix_id=%d", playToken, id);
	
	// Select the mix for play back
	// http://8tracks.com/sets/111696185/play.json?mix_id=14
	
	do {
		if(!first){
			snprintf(selectURL, 999, "http://8tracks.com/sets/%d/next.json?mix_id=%d", playToken, id); 
		}

		printf("%s\n", selectURL);
		text = request(selectURL);
		if(!text){
			return 1;
		}
		root = json_loads(text, 0, &error);
		if(!root) {
			fprintf(stderr, "error: on line %d: %s\n", error.line, error.text);
			return 1;
		}
		free(text);
		jsonset = json_object_get(root, "set");
		if(!json_is_object(jsonset)) {
			fprintf(stderr, "error: playset is not a object\n");
			json_decref(root);
			return 1;
		}
		jsontrack = json_object_get(jsonset, "track");
		if(!json_is_object(jsonset)) {
			fprintf(stderr, "error: playset is not a object\n");
			json_decref(root);
			return 1;
		}	
		struct track song;
		song.jsonartist = json_object_get(jsontrack, "performer");
		song.artist = json_string_value(song.jsonartist);
		printf("Artist: %s\t\t", song.artist);
		song.jsontrackname = json_object_get(jsontrack, "name");
		song.trackname = json_string_value(song.jsontrackname);
		printf("Track: %s\t\t", song.trackname);
		song.jsonid = json_object_get(jsontrack, "id");
		song.id = json_integer_value(song.jsonid);
		printf("Song ID: %d\t\t\n", song.id);
//		song.jsonat_beginning = json_object_get(jsontrack, "at_beginning");
//		song.at_beginning = json_is_true(song.jsonat_beginning);
//		printf("At beginning: %d\n", song.at_beginning);
		song.jsonat_last_track = json_object_get(jsontrack, "at_last_track");
		song.at_last_track = json_is_true(song.jsonat_last_track);
		printf("At end: %d\n", song.at_last_track);
		song.jsonstreamURL = json_object_get(jsontrack, "url");
		song.streamURL = json_string_value(song.jsonstreamURL);
		//printf("StreamURL: %s\n", song.streamURL);
	
		// Set the filename to the format artist - trackname.mp3
		// TODO: Download files to a file in ~/8linux/playlist/
		// TODO: Get cover art for the playlist as well
		snprintf(song.filename, 999, "%s - %s.mp3", song.artist, song.trackname);
		//snprintf(song.filename, 999, "%s/8tracks/%s/%s - %s.mp3", home, mixName, song.artist, song.trackname);
		//printf("Filename: %s\n", song.filename);
		// Download the file
		// TODO: set mp3 id3 tags
		if(request_to_file(song.streamURL, song.filename) != 0){
			fprintf(stderr, "Downloading %s failed.\n", song.filename);
			return -1;
		}
		
		// Report a "performance"
		// curl http://8tracks.com/sets/111696185/report.json?track_id=[track_id]&mix_id=[mix_id]
		snprintf(selectURL, 999, "http://8tracks.com/sets/%d/report.json?track_id=%d&mix_id=%d", playToken, song.id, id); 
		// printf("Performance URL: %s\n", selectURL);
		printf("Waiting to report a performance (30s)\n");
		sleep(30); //30s
		text = request(selectURL); // Report a performance
		printf("Reported. Waiting to start next song. (1min 30s)\n");
		// TODO: Get track length and wait a little bit less than that time (-10s)
		sleep(90); //90s
		first = 0; //Not a pretty way of handling it but oh well
		// at_last_track = song.at_last_track; // Same as above
		at_last_track = song.at_last_track;
	} while (!at_last_track);

	printf("All done!.\n");
	return 0;
}

// curl --header "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df" http://8tracks.com/musicbeats/programming.json
// curl --header "X-Api-Key: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df" http://8tracks.com/sets/new.json
