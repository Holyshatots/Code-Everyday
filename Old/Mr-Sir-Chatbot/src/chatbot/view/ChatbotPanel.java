package chatbot.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import chatbot.controller.AppController;

/**
 * Object that is the main GUI panel
 */
public class ChatbotPanel extends JPanel
{
	/*
	 * The chatbot AppController instance.
	 */
	private AppController baseController;
	/*
	 * A sample button for testing 
	 */
	private JButton sampleButton;
	/*
	 * A sample GUI text field
	 */
	private JTextField sampleField;
	private JTextArea chatArea;
	private JScrollPane chatPane;
	private JLabel lblSay;
	/*
	 * A GUI SpringLayout to be used for the Panel
	 */
	private SpringLayout baseLayout;
	
	/**
	 * Constructor for the panel
	 * @param baseController Reference to the AppController instance
	 */
	public ChatbotPanel(AppController baseController)
	{
		this.baseController = baseController;
		sampleButton = new JButton("Enter");
		sampleField = new JTextField(25);
		chatArea = new JTextArea(5, 25);
		chatPane = new JScrollPane(chatArea);
		lblSay = new JLabel("Say:");
		baseLayout = new SpringLayout();

		setupScrollPane();
		setupChatArea();
		
		/* Panel needs to be setup before it is laid out */
		setupPanel(); 
		setupLayout();
		setupListeners();
	}
	
	private void setupChatArea()
	{
		chatArea.setEditable(false);
	}
	
	private void setupScrollPane()
	{
		chatArea.setLineWrap(true);
	}
	
	/**
	 * Sets up the panel settings and adds components.
	 */
	private void setupPanel()
	{
		this.setBackground(Color.CYAN);
		this.setLayout(baseLayout);
		
		this.add(sampleButton);
		this.add(sampleField);
		this.add(chatPane);
		this.add(lblSay);
		
	}
	
	/*
	 * Dumping ground for auto-generated code from WindowBuilder
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, sampleButton, 6, SpringLayout.SOUTH, chatPane);
		baseLayout.putConstraint(SpringLayout.NORTH, sampleField, 7, SpringLayout.SOUTH, chatPane);
		baseLayout.putConstraint(SpringLayout.EAST, sampleField, -6, SpringLayout.WEST, sampleButton);
		baseLayout.putConstraint(SpringLayout.WEST, sampleButton, 315, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, sampleButton, 0, SpringLayout.EAST, chatPane);
		baseLayout.putConstraint(SpringLayout.SOUTH, chatPane, -37, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, chatPane, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, chatPane, 390, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, chatPane, 10, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, sampleField, 6, SpringLayout.EAST, lblSay);
		baseLayout.putConstraint(SpringLayout.NORTH, lblSay, 10, SpringLayout.SOUTH, chatPane);
		baseLayout.putConstraint(SpringLayout.WEST, lblSay, 0, SpringLayout.WEST, chatPane);
	}
	
	/*
	 * Sets up the listeners for the GUI components
	 */
	private void setupListeners()
	{
		sampleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String userTypedText = sampleField.getText();
				String botResponse = baseController.sendTextToChatbot(userTypedText);
				displayTextToUser(userTypedText);
				displayTextToUser(botResponse);
				sampleField.setText("");
			}
		});
	}
	
	public void displayTextToUser(String input)
	{
		chatArea.append("\n" + input);
	}
}
