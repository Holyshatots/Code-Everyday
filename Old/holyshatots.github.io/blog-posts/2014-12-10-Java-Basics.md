---
layout: post
title: "The Basics of Java"
date:   2014-12-10 00:00:00
image: /assets/article_images/2014-12-10-The-Basics-of-Java/James_Gosling_2008.jpg
---
James Gosling 

This is just a 

## What is Java?

Java is a programming language that is class-based, object-oriented, and concurrent. Code written in Java can be run on any Java Virtual Machine (JVM).
This allows Java to be run on all platforms as long as there is a JVM available for the platform. Java is also the main language used in Android development.
There were five primary goals in the creation of the Java platform:

1. It must be "simple, object-oriented and familiar"
2. It must be "robust and secure"
3. It must be "architecture-neutral and portable"
4. It must execute with "high performance"
5. It must be "interpreted, threaded, and dynamic"


### Differences in Java and C

The main differences between Java and C are the object-orientedness of the languages. C is not object-oriented (although it can be kind of made to be) and Java is built to be object-oriented from the beginning. C is also a compiled language which means it is translated into machine code while Java is interpreted at runtime by the Java Virtual Machine. Memory management in Java is made to be simple whereas in C it is clunky but efficient to a skilled programmer. 

## Basic Java Definitions



## Examples

### Hello World Class

```java

// class named "chatbot"
public class Chatbot
{
	// Beginning of constructor
	public Chatbot()
	{
		// Does nothing right now
	} // End of constructor

	// First method to run
	public static void main(String[] args)
	{
		// Print out "Hello World."
		System.out.println("Hello World.");
	}
}
```

This class does basically nothing. It only includes a main method and prints out the string "Hello World." It also includes a constructor that currently does nothing. If you save this as "Chatbot.java" and run it you will get Hello World. and then a program exit. (If you need help setting up a development environment I would recommend Eclipse. I may make a tutorial on using Eclipse and basic things to make it more usable.)


### A Basic GUI Program

This is the frame of the program. The frame is the what contains the panel which in turn contains the components of the program. Every GUI program must include a frame to have a panel. 

```java
package chatbot.view

import javax.swing.JFrame;

import chatbot.controller.AppController;

public class ChatbotFrame extends JFrame
{
	private Chatbotpanel basePanel;

	public ChatbotFrame(AppController baseController)
	{
		basePanel = new ChatbotPanel(baseController);
		setupFrame();
	}

	private void setupFrame()
	{
		this.setSize(400, 400);
		this.setVisible(true);
	}
}
```

This is a basic panel that is contained in the JFrame. It contains a simple button that when clicked does nothing. 

``` java

import javax.swing.*;

import chatbot.controller.AppController;

public class ChatbotPanele extends JPanel
{
	private AppController baseController;
	private Button button;

	public ChatbotPanel(AppController baseController)
	{
		this.baseController = baseController;
		button = new Button();

		// Panel needs to be setup before it is laid out
		setupPanel();
		setupLayout();
		setupListeners();
	}

	private void setupPanel()
	{

	}

	private void setupLayout()
	{

	}

	private void setupListeners()
	{
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				// Do something when the button is clicked
			}
		});
	}
}
```
