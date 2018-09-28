---
layout: post
title: Reddit's Favorite Word
date:   2014-10-25 00:00:00
---
## Design
This study is being done to find the most used words in titles of posts on various subreddits recently. A subreddit is a
a community on the website [Reddit](http://www.reddit.com). To gather this information, I created a Java program
that gets it's information from the newest posts on that subreddit. Common english words like the, are, and were removed
from the data. The most recent 1000 posts off of each subreddit were analyzed.

## Bias
A possible source of bias is the activity of the individual subreddits. Meaning, very active communities have more new posts so
the topics of the titles are more recent. Trending topics like Ebola will be under-represented in less active communities. Another
source of bias that I accounted for was instead of using the most popular posts I used the newest post. This is because 
a small subset of users are consistently given more votes based on the popularity of the user. The new posts give a better
representation of what the community is actually thinking.

## Data and Findings
Below are the top 10 used words and the frequency of each of them.
The subreddits are picked from the top 3 most active (funny, AdviceAnimals, and pics) along with ones from my own
personal preference.

### /r/SaltLakeCity
![SaltLakeCity Graph](http://blog.holyshatots.com/images/SaltLakeCityChart.png)


- 16 : park
- 17 : downtown
- 19 : need
- 20 : know
- 23 : best
- 28 : good
- 49 : utah
- 53 : city
- 66 : slc
- 70 : lake


### /r/dataisbeautiful
![dataisbeautiful Graph](http://blog.holyshatots.com/images/dataisbeautifulChart.png)

Some context is needed to understand this chart. OC is a term for "original content" and is used often on sites like Reddit. 

- 10 : visualization
- 11 : global
- 12 : countries
- 13 : country
- 14 : time
- 17 : interactive
- 21 : ebola
- 22 : data
- 23 : map
- 113 : oc


### /r/funny
![funny Graph](http://blog.holyshatots.com/images/funnyChart.png)


- 11 : ebola
- 12 : know
- 13 : dont
- 14 : little
- 15 : reddit
- 16 : guy
- 17 : today
- 19 : new
- 22 : think
- 24 : im
- 26 : just

### /r/AdviceAnimals
![AdviceAnimals Graph](http://blog.holyshatots.com/images/AdviceAnimalsChart.png)


- 14 : said
- 15 : work
- 16 : today
- 17 : guy
- 19 : know
- 23 : think
- 25 : dont
- 26 : im
- 33 : time
- 42 : just

### /r/pics
![pics Graph](http://blog.holyshatots.com/images/picsChart.png)

- 13 : did
- 14 : right
- 15 : friend
- 16 : friends
- 17 : picture
- 18 : costume
- 20 : time
- 25 : just
- 32 : today
- 40 : halloween



All code for this project can be found on [my Github](https://github.com/Holyshatots/RedditStats/)
