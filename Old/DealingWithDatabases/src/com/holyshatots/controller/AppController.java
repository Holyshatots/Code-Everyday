package com.holyshatots.controller;

import com.holyshatots.view.CoolPanel;
import com.holyshatots.view.FirstFrame;

public class AppController
{
	private FirstFrame frame;
	private CoolPanel panel;
	private DatabaseController dataController;
	
	public AppController()
	{
		dataController = new DatabaseController(this);
		frame = new FirstFrame(this);
		panel = new CoolPanel(this);	
		
		setupLayout();
		setupListeners();
	}
	
	/**
	 * Setup the layout
	 */
	public void setupLayout()
	{
		frame.add(panel);
		panel.setSize(1000, 1000);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}
	
	public void setupListeners(){
		
	}
	
	/**
	 * Returns the database controller reference
	 * @return reference to the dbcontroller
	 */
	public DatabaseController getDataController()
	{
		return this.dataController;
	}
	
	/**
	 * Returns a reference to the frame
	 * @return reference to the app frame
	 */
	public FirstFrame getAppFrame()
	{
		return frame;
	}

	/**
	 * Begin the problem
	 */
	public void start()
	{
		
	}
}
