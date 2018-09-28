package com.holyshatots.shared;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DomainObject implements Serializable
{
	private ArrayList<String> words = new ArrayList<String>();
	
	public DomainObject()
	{
	}

	public DomainObject(List<String> words)
	{
		this.words.addAll(words);
	}

	/**
	 * Getter for the words object
	 * @return
	 */
	public List<String> getWords()
	{
		return words;
	}
	
	/**
	 * Appends words to the list
	 * @param words
	 */
	public void addWords(List<String> words)
	{
		this.words.addAll(words);
	}

}
