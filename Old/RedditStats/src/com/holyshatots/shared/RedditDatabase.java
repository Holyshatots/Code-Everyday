package com.holyshatots.shared;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.DatatypeConverter;



public class RedditDatabase
{
	private final String baseURL;
	private long currentKey;
	private HttpURLConnection connection;
	private static String API_KEY;
	private DatatypeConverter converter;
	private int httpStatus;
	
	public RedditDatabase(String API_KEY)
	{
		baseURL = "https://api.orchestrate.io/v0/WordsOnReddit/";
		this.API_KEY = API_KEY; // ADD API_KEY
		currentKey = 0;
	}
	
	/*
	static class MyAuthenticator extends Authenticator
	{
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication (API_KEY, "");
		}
	}
	*/
	
	public void addEntry(String title, String subreddit, int votes, long timeUTC) throws Exception
	{
		OutputStreamWriter out;
		InputStream in;
		
		URL dbURL = new URL(baseURL + currentKey);
		System.out.println(baseURL + currentKey);
		connection = (HttpURLConnection) dbURL.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		
		API_KEY = API_KEY + ":";
		String encoding = converter.printBase64Binary(API_KEY.getBytes());
		connection.setRequestProperty("Authorization", "Basic " + encoding);
		
		String data = "{ \"name\": \"" + title + 
				"\", \"subreddit\": \"" + subreddit + 
				"\", \"votes\": " + votes +
				", \"timeUTC\": " + timeUTC + " }";
		
		// String testData = "{ \"name\": \"Black Chrome Tron Lamborghini Aventador\", \"subreddit\": \"pics\", \"votes\": 535, \"timeUTC\": 1414255044 }"; 
		
		try {
			
			/* Send the request and check response */
			out = new OutputStreamWriter(connection.getOutputStream());
			out.write(data);
			//out.write(testData);
			System.out.println("Wrote to database: " + data + " with key: " + currentKey);
			httpStatus = connection.getResponseCode();
			System.out.println("Server responded with response code: " + httpStatus);
			if(httpStatus == 201)
			{
				// Correctly responded with 201 CREATED
				increaseKey();
			} else 
			{
				System.out.println("Entry not created");
			}
			
			// TODO Check response
			in = connection.getInputStream();
			for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
			    System.out.println(header.getKey() + "=" + header.getValue());
			}
			printInputStream(in);
			
			in.close();
			out.close();
		} catch (IOException e) {
			// Horrible networking error
		} finally {

			connection.disconnect();
		}

	}
	
	public void increaseKey()
	{
		currentKey++;
	}
	
	public void printInputStream(InputStream is)
	{
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		
		try
		{
			while((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);
			}
			in.close();
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
