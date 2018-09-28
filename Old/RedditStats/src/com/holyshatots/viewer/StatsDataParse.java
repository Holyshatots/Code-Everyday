package com.holyshatots.viewer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.holyshatots.shared.DomainObject;
import com.holyshatots.shared.OrchestrateDatabase;


public class StatsDataParse
{
	OrchestrateDatabase db;
	String collection;
	String apiKey;
	
	public StatsDataParse()
	{
		collection = "WordsOnReddit";
		apiKey = "ee66e1bc-eed7-4d1f-bdcf-4fda635eab6f";
		
		db = new OrchestrateDatabase(apiKey, collection);
	}
	
	public void wordFrequency(String subreddit)
	{
		List<String> words;
		Map<String, Integer> map = new HashMap<>();
		
		DomainObject data = db.getEntry(subreddit);
		words = data.getWords();
		
		Iterator<String> i = words.iterator();
		
		// Create map of word frequency
		while(i.hasNext())
		{
			String w = i.next();
			Integer n = map.get(w);
			n = (n == null) ? 1 : ++n;
			map.put(w, n);
			// System.out.println(w + " : " + n);
		}
		Map<Integer, String> reversedMap = new TreeMap<Integer, String>();
		for (Map.Entry entry : map.entrySet()) {
			reversedMap.put((Integer) entry.getValue(), (String) entry.getKey());
		}
		
		for (Map.Entry entry : reversedMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		

	}
	
}
