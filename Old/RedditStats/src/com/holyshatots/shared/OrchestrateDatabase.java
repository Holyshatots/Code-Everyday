package com.holyshatots.shared;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.orchestrate.client.*;
import io.orchestrate.client.dao.GenericAsyncDao;


public class OrchestrateDatabase
{
	private Client client;
	private String collection;
	private String API_KEY;
	private String[] stopwords = {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost",
            "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an", "and",
            "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became",
            "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides",
            "between", "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
            "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else",
            "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few",
            "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from",
            "front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
            "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself",
            "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into",
            "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many",
            "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must",
            "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none",
            "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto",
            "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps",
            "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
            "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something",
            "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their",
            "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon",
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru",
            "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until",
            "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
            "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while",
            "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
            "you", "your", "yours", "yourself", "yourselves", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1.", "2.", "3.", "4.", "5.", "6.", "11",
            "7.", "8.", "9.", "12", "13", "14",
            "terms", "conditions", "values", "interested.", "care", "sure", 
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "contact", "grounds", "buyers", "tried", "said,", "plan", "value", "principle.", "forces", "sent:", "is,", "was", "like",
            "discussion", "tmus", "diffrent.", "layout", "area.", "thanks", "thankyou", "hello", "bye", "rise", "fell", "fall", "psqft.", "http://", "km", "miles"};

	
	public OrchestrateDatabase(String API_KEY, String collection)
	{
		this.API_KEY = API_KEY;
		this.collection = collection;
		client = OrchestrateClient.builder(API_KEY).build();
	}
	
	/**
	 * Adds an entry or appends the wordlist if the key already exists
	 * @param title The title of the post
	 * @param subreddit The subreddit/key
	 */
	public void addEntry(String title, String subreddit)
	{
		DomainObject data;
		
		// Split the title into individual words, remove non-letter characters, 
		// change to lower case
		String[] split = title.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		List<String> wordList = new LinkedList<String>(Arrays.asList(split));
		Iterator<String> i = wordList.iterator();
		while(i.hasNext())
		{
			String a = i.next();
			for ( String tempStop : stopwords)
			{
				if(a.equals(tempStop))
				{
					// The word matches a word on the stop list
					i.remove();
					break;
				}
			}
		}
		
		// Check if there is already an entry
		if((data = getEntry(subreddit)) == null)
		{
			data = new DomainObject();

		}
		
		//System.out.println("Adding entry with key:" + subreddit);
		data.addWords(wordList);
		
		// Submit to database
		final KvMetadata kvMetadata = 
				client.kv(collection, subreddit)
					.put(data)
					.get();
		
	}

	
	/**
	 * Gets the entry based off of the key, which is the subreddit
	 * @param subreddit The key for the database
	 */
	public DomainObject getEntry(String subreddit)
	{
		KvObject<DomainObject> object =
				client.kv(collection, subreddit)
					.get(DomainObject.class)
					.get();
		
		if(object == null)
		{
			// received a 404, the object doesn't exist
			// System.out.println( + " does not exist");
			return null;
		} else {
			DomainObject data = object.getValue();
			return data;
		}
	}
	
}
