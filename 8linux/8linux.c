/* Mitch McAffee
 ***************************************************
8Linux
API KEY: faeab0bca799a7af3c21d9e3d3a88b4b28dc30df
Basic program that will download playlists from 8tracks
* Uses libcurl for http 
* libxml2 for parsing the xml returned
*/

#include <stdio.h>
#include <stdlib.h>
#include <curl/curl.h>
#define API_KEY faeab0bca799a7af3c21d9e3d3a88b4b28dc30df

int main(int argc, char *argv[]){
	CURL *curl;
	CURLcode res;
	
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://8tracks.com");
		
		res = curl_easy_perform(curl);
		if(res != CURLE_OK)
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		
		curl_easy_cleanup(curl);
	}
	return 0;
}
