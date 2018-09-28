# reddit-videos
Creates a playlist of youtube videos for a given subreddit.

I use amazon's api gateway to create a free (as long as I keep it under 1
million requests) http proxy that can get around cross site request origin
policies. This allows the site to get the listing
of subreddits and youtube embedding code from client code.
