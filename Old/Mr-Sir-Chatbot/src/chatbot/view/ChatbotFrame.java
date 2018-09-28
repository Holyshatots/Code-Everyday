package chatbot.view;

import javax.swing.JFrame;

import chatbot.controller.AppController;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

/**
 * The frame of the Chatbot GUI
 */
public class ChatbotFrame extends JFrame
{
	/**
	 * A GUI panel to be put into the frame
	 */
	private ChatbotPanel basePanel;
	private SpringLayout springLayout;
	private JSeparator separator;
	
	/**
	 * Constructor that initializes the frame
	 * @param baseController A reference to the Chatbot's controller
	 */
	public ChatbotFrame(AppController baseController)
	{
		basePanel = new ChatbotPanel(baseController);
		springLayout = (SpringLayout) basePanel.getLayout();
		separator = new JSeparator();
		
		basePanel.add(separator);
		
		setupLayout();
		
		setupFrame();
	}
	
	private void setupLayout()
	{
		springLayout.putConstraint(SpringLayout.NORTH, separator, -35, SpringLayout.SOUTH, basePanel);
		springLayout.putConstraint(SpringLayout.WEST, separator, 192, SpringLayout.WEST, basePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, -33, SpringLayout.SOUTH, basePanel);
		springLayout.putConstraint(SpringLayout.EAST, separator, -3, SpringLayout.WEST, basePanel);
	}

	/*
	 * Sets the settings of the frame
	 */
	private void setupFrame()
	{
		this.setContentPane(basePanel); // Needs to be called
//		this.setLocation(67, 100);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setVisible(true); // Must be last
	}
}
