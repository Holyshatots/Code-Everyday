package com.holyshatots.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.holyshatots.controller.AppController;

public class CoolPanel extends JPanel
{
	private JButton queryButton;
	private JTextArea displayArea;
	private AppController baseController;
	private JTable resultsTable;
	private JScrollPane displayPane;
	
	public CoolPanel(AppController baseController)
	{
		this.queryButton = new JButton("Click here to test the query");
		this.displayArea = new JTextArea(10,30);
		this.baseController = baseController;
		this.resultsTable = new JTable();
		setupDisplayPane();
		setupTable();
		setupListeners();
	}
	
	/**
	 * Setup the display pane
	 */
	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
	}
		
	/**
	 * Setup the table
	 */
	private void setupTable()
	{
		DefaultTableModel basicData = new DefaultTableModel(baseController.getDataController().testResults(),
															baseController.getDataController().getMetaDataTitles());
		resultsTable = new JTable(basicData);
		displayPane = new JScrollPane(resultsTable);
	}

	/**
	 * Setup the listeners for the buttons
	 */
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String results = baseController.getDataController().displayTables();
				displayArea.setText(results);
			}
		});
	}
	

}
