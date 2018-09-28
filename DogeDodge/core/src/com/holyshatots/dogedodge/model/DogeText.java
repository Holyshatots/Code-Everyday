package com.holyshatots.dogedodge.model;

import com.badlogic.gdx.graphics.Color;



public class DogeText
{
	private int x;
	private int y;
	private String text;
	private Color color;
	
	public DogeText(String text, int x, int y, Color color)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public String getText()
	{
		return text;
	}
	public Color getColor()
	{
		return color;
	}
}
