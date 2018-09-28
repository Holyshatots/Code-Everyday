package com.holyshatots.crawler;
import com.holyshatots.viewer.StatsDataParse;


public class GatherRunner
{
	private static StatsController controller;
	
	public static void main(String args[])
	{
		controller = new StatsController();
		controller.start();
	}
}
