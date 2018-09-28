package view;

import java.awt.Panel;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MainPanel extends Panel
{
	private JTextField textField;
	public MainPanel() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		JTextArea textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, 263, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 440, SpringLayout.WEST, this);
		add(textArea);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 7, SpringLayout.SOUTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 6, SpringLayout.SOUTH, textArea);
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnNewButton);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, this);
		add(btnNewButton);
	}
}
