package chatbot.model;

import java.util.ArrayList;

/**
 * Chatbot object that does the processing
 * @author Mitch McAffee
 */
public class Chatbot
{
	private String name;
	private int numberOfChats;
	private ArrayList<String> memeList;
	private ArrayList<String> userInputList;
	private Dictionary dictionary;

	private ChatUser myUser;
	
	/**
	 * Creates a Chatbot object with a specified name. Initializes the total
	 * chats to 0.
	 * @param name The name of the chatbot
	 */
	public Chatbot(String name)
	{
		this.name = name; // this. means talk to the current object
		numberOfChats = 0;
		dictionary = new Dictionary();
		
		userInputList = new ArrayList<String>();
		memeList = new ArrayList<String>();
		fillTheMemeList();
		
		myUser = new ChatUser();
	}
	
	/**
	 * Getter for chatbot name
	 * @return The name of the chatbot
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the chatbot name.
	 * @param name The new name of the chatbot
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Getter for the number of chats.
	 * @return The current number of chats
	 */
	public int getNumberOfChats()
	{
		return numberOfChats;
	}

	/**
	 * Increments the number of chats by 1.
	 */
	public void incrementChats()
	{
		numberOfChats++;
	}
	
	/**
	 * Populates the memeList
	 */
	private void fillTheMemeList()
	{
		memeList.add("y u mad bro");
		memeList.add("doh!");
		memeList.add("idon'teven");
		memeList.add("Ain't nobody got time fo dat");
		memeList.add("Memes, memes everywhere");
	}
	
	/**
	 * Processed the supplied text from the user to provide a message from the Chatbot.
	 * TODO : Implementation of alice
	 * @param userText The user supplied text.
	 * @return What the Chatbot says back.
	 */
	public String processText(String userText)
	{
		String processedText = "";
		if(getNumberOfChats() == 0) 
		{
			processedText = "What's your age, " + userText + "?";
			myUser.setName(userText);
		}
		else if (getNumberOfChats() == 1)
		{
			int age = Integer.parseInt(userText);
			myUser.setAge(age);
			processedText += "What is your favorite movie since you are " + age + " years old?";
		}
		else if (memeChecker(userText))
		{
			processedText = "hey, you found a meme: " + userText + " isn't that cool.";
		} 
		else if (periwinkleChecker(userText))
		{
			processedText = "Hey, periwinkle is my favorite color!";
		}
		else if (orangeredChecker(userText))
		{
			processedText = "Hmm.. orangered is ok I guess..";
		}
		else if (dictionaryChecker(userText))
		{
			processedText = dictionary.process(userText);
		}
		else if (addToListChecker(userText))
		{
			userInputList.add(0, addToListProcess(userText));
			processedText = "Added " + userText + " to the list.";
		}
		else if (userInputChecker(userText))
		{
			processedText = "Removed " + userText + " from the list.";
		}
		else if (liftsChecker(userText))
		{
			// Impossible
			System.exit(0);
			
			// explode();
		}
		else
		{
			processedText = "Of course, sir.";
		}
		
		incrementChats();
		return processedText;
	}
	
	private String addToListProcess(String userText)
	{
		String processedText = null;
		processedText = String.valueOf(userText.subSequence(userText.indexOf("\"") + 1, userText.lastIndexOf("\"")));
		
		return processedText;
	}

	private boolean addToListChecker(String userText)
	{
		return userText.matches("add \".\"");
	}
	
	/**
	 * Checks if the usertext contains the string alice
	 * @param userText
	 * @return
	 */
	private boolean aliceChecker(String userText)
	{
		boolean isAlice = false;
		String[] splitText = userText.split(" ");
		for(String text : splitText)
		{
			if(text.equalsIgnoreCase("alice"))
			{
				isAlice = true;
			}
		}
		return isAlice;
	}
	
	/**
	 * Checks for the presence of a meme in the currentText.
	 * @param currentText The user supplied text.
	 * @return If the string contains a meme.
	 */
	private boolean memeChecker(String currentText)
	{
		boolean isAMeme = false;
		
		for(String currentMeme : memeList)
		{
			if(currentMeme.equalsIgnoreCase(currentText))
			{
				isAMeme = true;
			}
		}
		
		return isAMeme;
	}
	
	// I know, it's a joke
	private boolean liftsChecker(String currentText)
	{
		boolean liftsBro = false;
		// http://bretcontreras.com/wp-content/uploads/Do-you-even-lift.png
		return liftsBro;
	}
	
	/*
	 * Checks for the word periwinkle in user text
	 * @param currentText The user supplied text
	 * @return If the string contains periwinkle
	 */
	private boolean periwinkleChecker(String userText)
	{
		boolean isPeriwinkle = false;
		
		String[] splitText = userText.split(" ");
		for(String text : splitText)
		{
			if(text.equalsIgnoreCase("periwinkle"))
			{
				isPeriwinkle = true;
			}
		}
		
		return isPeriwinkle;
	}
	
	/**
	 * Checks for the word orangered in user text
	 * @param userText
	 * @return If the string contains orangered
	 */
	private boolean orangeredChecker(String userText)
	{
		boolean isOrangered = false;
		
		String[] splitText = userText.split(" ");
		for(String text : splitText)
		{
			if(text.equalsIgnoreCase("orangered"))
			{
				isOrangered = true;
			}
		}
		
		return isOrangered;
	}
	
	/**
	 * Checks if the search is a dictionary search in the form:
	 * define "String"
	 * @param userText
	 * @return
	 */
	private boolean dictionaryChecker(String userText)
	{
		boolean isDictionarySearch = false;
		return isDictionarySearch;
	}
	
	
	private boolean userInputChecker(String userText)
	{
		boolean matchesInput = false;
		
		if(userInputList.size() > 0)
		{
			for(int loopCount = 0; loopCount < userInputList.size(); loopCount++)
			{
				if(userText.equalsIgnoreCase(userInputList.get(loopCount)))
				{
					matchesInput = true;
					userInputList.remove(loopCount);
					loopCount--; // Need to go back one because one got removed
				}
			}
		}
		
		return matchesInput;
	}
	
	/**
	 * Checks if it is ok to quit. A true means it is ok to quit.
	 * @param input String from user to check if it is ok to quit
	 * @return Returns if it is ok to quit
	 */
	public boolean quitChecker(String input)
	{
		boolean okToQuit = false;
		
		if(input != null && (input.equalsIgnoreCase("goodbye") || input.equalsIgnoreCase("yes")))
		{
			okToQuit = true;
		}
		
		return okToQuit;
	}
}
