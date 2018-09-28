package chatbot.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import chatbot.model.Chatbot;
import chatbot.view.ChatbotFrame;
import chatbot.view.ChatbotPanel;


/**
 * Application Controller for the Chatbot String manipulation project.
 * Responsible for controlling the View and Model packages.
 * @author Mitch McAffee
 * @version 1.2.2 10/1/14
 */
public class AppController
{
	/**
	 * The Chatbot Model instance
	 */
	private Chatbot myBot;
	
	//private ChatterBean myAliceBot;
	
	/**
	 * The startup message for our chatbot application
	 */
	private String startMessage;
	
	/**
	 * GUI frame for the application
	 */
	private ChatbotFrame baseFrame;
	
	private ChatbotPanel appPanel;
	
	private ArrayList<String> botNameList;
	
	private String currentBot;
	
	/**
	 * Creates a AppController and initializes the associated View and Model Objects
	 */
	public AppController()
	{
		baseFrame = new ChatbotFrame(this);
		appPanel = (ChatbotPanel) baseFrame.getContentPane();
		myBot = new Chatbot("Mr. Sir");
		startMessage = "Welcome to the " + myBot.getName() + " Chatbot, type in your name.";
		//myAliceBot = new ChatterBean("res/properties.xml");
		botNameList.add("chatbot");
		//botNameList.add("alice");
		setCurrentBot("chatbot"); // Default to chatbot
	}
	
	/**
	 * Getter for Chatbot object.
	 * @return Returns the Chatbot object for this app.
	 */
	public Chatbot getMyBot()
	{
		return myBot;
	}
	
	/**
	 * Starts the Chatbot application
	 */
	public void start()
	{	
		appPanel.displayTextToUser(startMessage);
	}
	
	public String sendTextToChatbot(String userInput)
	{
		String respondText = "";
		
		respondText = myBot.processText(userInput);
		
		return respondText;
	}
	
	/**
	 * Exits the program
	 */
	private void quit()
	{
		System.exit(0);
	}

	public String getCurrentBot()
	{
		return currentBot;
	}

	public void setCurrentBot(String currentBot)
	{
		this.currentBot = currentBot;
	}
	
	/**
	 * Read text from a hardcoded text file to reload a saved conversation
	 * @return
	 */
	public String readTextFromFile()
	{
		String fileText = "";
		String filePath = "/Users/CodyH/Documents/";
		String fileName = filePath + "saved text.txt";
		File inputFile = new File(fileName);
		
		try 
		{
			Scanner fileScanner = new Scanner(inputFile);
			
			while(fileScanner.hasNext())
			{
				fileText += fileScanner.nextLine() + "\n";
			}
			
			fileScanner.close();
		}
		catch(FileNotFoundException fileException)
		{
			JOptionPane.showMessageDialog(baseFrame, "The file is not there");
		}
		
		return fileText;
	}
	
	/*
	public void saveText(String conversation, boolean appendToEnd)
	{
		String fileName = "";
		
		PrintWriter outputWriter;
		if(appendToEnd)
		{
			try
			{
				outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, appendToEnd)));
				outputWriter.append(conversation);
				outputWriter.close();
			}
			catch(FileNotFoundException fileError)
			{
				
			}
			catch(IOException inputOutputError)
			{
				
			}
		}
		else
		{
			try
			{
				outputWriter = new PrintWriter(fileName);
				outputWriter.println(conversation);
				outputWriter.close();
			}
			catch(FileNotFoundException exception)
			{
				
			}
		}
	}
	*/
}

