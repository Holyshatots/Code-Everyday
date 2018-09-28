package com.holyshatots.crawler;

import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import org.json.*;

import com.holyshatots.shared.OrchestrateDatabase;


public class StatsGather
{
	static URL redditRandom;
	static URLConnection yc;
	String collection;
	String apiKey;
	
	OrchestrateDatabase db;
	
	public StatsGather()
	{
		collection = "WordsOnReddit";
		apiKey = "ee66e1bc-eed7-4d1f-bdcf-4fda635eab6f";
		
		db = new OrchestrateDatabase(apiKey, collection);
	}
	
	/**
	 * Currently experimental and not fully implemented
	 * 
	 * @param postsToGet
	 * @param subreddit
	 */
	public void getNewPostRandom(int postsToGet, String subreddit)
	{
		String inputLine;
		String title = null;
		String lastTitle = null;
//		int votes;
//		int timeUTC;
		Boolean nsfw;

		
		/* Reddit JSON Structure */
		JSONArray baseArray;
		JSONObject text;
		JSONObject baseData;
		JSONArray children;
		JSONObject childrenBase;
		JSONObject data;

		try
		{
			redditRandom = new URL("http://www.reddit.com/r/" + subreddit + "/random.json");
		}
		catch (MalformedURLException e)
		{
			System.out.println("MalformedURLException");
		}
		
		for(int i=0; i <= postsToGet; i++)
		{

			try {
				yc = redditRandom.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
				
				if ((inputLine = in.readLine()) != null)
				{
					System.out.println(inputLine);
					
					
					/* Navigate the JSON Structure */ 
					baseArray = new JSONArray(inputLine);
					text = baseArray.getJSONObject(0);
					baseData = text.getJSONObject("data");
					children = baseData.getJSONArray("children");
					childrenBase = children.getJSONObject(0);
					data = childrenBase.getJSONObject("data");
					
					
					title = data.getString("title");
					subreddit = data.getString("subreddit");
					// votes = data.getInt("score");
					// timeUTC = data.getInt("created_utc");
					nsfw = data.getBoolean("over_18");
					
					if(title.equals(lastTitle)) {
						System.out.println("Duplicate entry!");
					} else {
						
						System.out.println("Title: " + title + " , " +
								"Subreddit: " + subreddit + " , " + 
						//		"Votes: " + votes + " , " +
						//		"TimeUTC: " + timeUTC + " , " +
								"Over 18: " + nsfw);
						
						/* Check for dupes and nsfw */
						if(nsfw) {
							// NSFW
							System.out.println("Adult! Skipping..");
						} else {
							db.addEntry(title, subreddit);
						}
					} 
					
					title = lastTitle;
				}
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			// Wait for 1 second
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
		} // end of for
	
	}
	
	/**
	 * Gets posts from the new feed and puts each individual word into the database
	 * @param totalHundredPostsToGet The number of results to get measured in hundreds
	 * @param subreddit The subreddit to pull from
	 */
	public void getNewPosts(int totalHundredPostsToGet, String subreddit)
	{
		String inputLine;
		String title = null;
		String lastTitle = null;
		String fullName = null;
		Boolean nsfw;
		int postsToGet = 100;

		
		/* Reddit JSON Structure */
		JSONObject text;
		JSONObject baseData;
		JSONArray postArray;
		JSONObject childrenBase;
		JSONObject data;

		/* Main loop*/
		for(int i=0; i < totalHundredPostsToGet; i++)
		{

			try 
			{
				/* Set up the connection */
				redditRandom = new URL("http://www.reddit.com/r/" + subreddit + "/new.json?limit=" + postsToGet + "&after=" + fullName);
				yc = redditRandom.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
				/* Parse the data */
				if ((inputLine = in.readLine()) != null)
				{
					//System.out.println(inputLine);
					
					/* Navigate the JSON Structure */ 
					text = new JSONObject(inputLine);
					baseData = text.getJSONObject("data");
					postArray = baseData.getJSONArray("children");
					
					for(int index=0; index < postsToGet; index++)
					{					
						childrenBase = postArray.getJSONObject(index);
						data = childrenBase.getJSONObject("data");
						
						title = data.getString("title");
						subreddit = data.getString("subreddit");
						fullName = data.getString("name");
						// votes = data.getInt("score");
						// timeUTC = data.getInt("created_utc");
						nsfw = data.getBoolean("over_18");
						System.out.println((index + i*100) + " -- Title: " + title + " , " +
								"Subreddit: " + subreddit + " , " + 
								"Over 18: " + nsfw);
						
						/* Check for dupes and nsfw */
						if(nsfw) {
							// NSFW
							System.out.println("Adult! Skipping..");
						} else {
							db.addEntry(title, subreddit);
						}
						if(index == 99)
						{
							// Last object
							
						}
					}
				} 
				
				// Cleanup
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			// Wait for 1 second
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			System.out.println("100 down.. Last full name: " + fullName);
		}
	}
}
