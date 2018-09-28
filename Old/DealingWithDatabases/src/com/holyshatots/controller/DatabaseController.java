package com.holyshatots.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private AppController baseController;
	
	public DatabaseController(AppController baseController)
	{
		this.baseController = baseController;
		this.connectionString = "jdbc:derby:DBTest;create=true";
//		closeConnection();
		checkDriver();
		setupConnection();
//		createTable();
		fillTable();
//		closeConnection();
	}
	
	/**
	 * Create the table test
	 */
	public void createTable() 
	{
		String query = "CREATE TABLE test( "
				+ "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "NAME VARCHAR(200) NOT NULL,"
				+ "CONSTRAINT primary_key PRIMARY KEY (ID)"
				+ ")";
		
		try 
		{
			Statement insertStatement = databaseConnection.createStatement();
			insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException exception)
		{
			displayErrors(exception);
		}
	}
	
	/**
	 * Fill the table with random data
	 * @return
	 */
	public int fillTable() 
	{
		int rowsAffected = -1;
		String query = "INSERT INTO test (name) VALUES ('Awesomeness')";
		
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		return rowsAffected;
	}
	
	/**
	 * Get the titles of the columns
	 * @return
	 */
	public String[] getMetaDataTitles()
	{
		String [] columns;
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			ResultSetMetaData answerData = answers.getMetaData();
			columns = new String[answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}

		return columns;
	}
	
	/**
	 * Test the results of a sql query
	 * @return
	 */
	public String[][] testResults()
	{
		String[][] results;
		String query = "SELECT * FROM test";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String[numberOfRows][1];
			
			while(answers.next())
			{
				results[answers.getRow()-1][0] = answers.getString(1);
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			results = new String[][] {{"empty"}};
			displayErrors(currentException);
		}
		
		return results;
	}
	
	/**
	 * Checks to make sure the correct sql driver is available
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/**
	 * Close the connection to the db
	 */
	public void closeConnection()
	{
		try {
			// Shutdown all of the databases
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			displayErrors(e);
		} 
	}
	
	/**
	 * Setup the connection for the db
	 */
	private void setupConnection()
	{
		try {
			databaseConnection = DriverManager.getConnection(this.connectionString);
		} catch (SQLException e) {
			displayErrors(e);
			System.exit(1);
		}
	}
	
	/**
	 * Display errors encountered during execution
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(),  "Exception: " + currentException.getMessage());
	}
	
	/**
	 * Insert a record into the db
	 * @return
	 */
	public int insertSample()
	{
		int rowsAffected = -1;
		String query = "INSERT INTO `gasoline_travel`.`cities` (`name, `population`) VALUES ('Him', 100);";
		
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		return rowsAffected;
	}
	
	/**
	 * Display the tables
	 * @return
	 */
	public String displayTables()
	{
		String tableNames = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				tableNames += answers.getString(1) + "\n";
			}
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return tableNames;
	}
}
