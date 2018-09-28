package com.holyshatots.crawler;

import javax.swing.JOptionPane;

import com.holyshatots.viewer.StatsDataParse;

public class StatsController
{
	private String subreddit;
	private Object[] options = {"Gather data and analyze",
								"Analyze already collected data"};
	
	StatsGather gather;
	StatsDataParse parser;
	
	
	public StatsController()
	{
		gather = new StatsGather();
		parser = new StatsDataParse();	
	}
	
	public void start()
	{
		subreddit = getSubredditFromUser();
		int choice = JOptionPane.showOptionDialog(null,
				  "What would you like to do?",
				  "Choose function",
				  JOptionPane.YES_NO_OPTION,
				  JOptionPane.QUESTION_MESSAGE,
				  null,
				  options,
				  options[1]);

		if(choice == JOptionPane.YES_OPTION) 
		{
			// Gather data and analyze
			gather.getNewPosts(getNumberOfTitles(), subreddit); // Get 10 * 100 = 1000 posts
		}
		else 
		{
			// Analyze already collected data
			parser.wordFrequency(subreddit); 
		}
	}
	
	private int getNumberOfTitles()
	{
		int numberOfTitles;
		numberOfTitles = Integer.parseInt(JOptionPane.showInputDialog(null,
													   "Number of hundred subreddits to analyze:",
													   "Subreddits",
													   JOptionPane.PLAIN_MESSAGE));
		
		return numberOfTitles;
	}
	
	private String getSubredditFromUser()
	{
		String subreddit;
		subreddit = (String) JOptionPane.showInputDialog(null, 
														 "Subreddit to analyze:", 
														 "Subreddit", 
														 JOptionPane.PLAIN_MESSAGE);
		
		return subreddit;
	}
}
