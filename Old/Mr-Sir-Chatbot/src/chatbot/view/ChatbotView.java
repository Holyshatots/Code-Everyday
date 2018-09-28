package chatbot.view;

import javax.swing.JOptionPane;

import chatbot.controller.AppController;

/**
 * Object to control what the user sees.
 * @author Mitch McAffee
 *
 */
public class ChatbotView
{
	/*
	 * A reference to the controller
	 */
	private AppController baseController;
	
	/**
	 * Constructor for ChatbotView
	 * @param baseController A reference to the AppController
	 */
	public ChatbotView(AppController baseController)
	{
		this.baseController = baseController;
	}
	
	/**
	 * Displays the input to the user via popup and returns the users response to the controller.
	 * @param input The text to be displayed.
	 * @return The users response.
	 */
	public String displayChatbotConversations(String input)
	{
		String output = "";
		
		output = JOptionPane.showInputDialog(null, input);
		
		
		return output;
	}
	
	
	/**
	 * Displays the supplied information.
	 * @param input The text to be displayed.
	 */
	public void displayInformation(String input)
	{
		
	}
}
