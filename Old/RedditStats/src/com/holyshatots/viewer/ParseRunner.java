package com.holyshatots.viewer;


public class ParseRunner
{
	static void main(String[] args)
	{
		String subreddit = "SaltLakeCity";
		StatsDataParse parser = new StatsDataParse();
		parser.wordFrequency(subreddit);
	}
}
