package questions.controller;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class Questions 
{
	private Scanner questionScanner;
	private int numberOfQuestions;
	
	public Questions()
	{
		questionScanner = new Scanner(System.in);
		numberOfQuestions = 2;
	}
	
	public void start()
	{
		//askQuestions();
		askQuestionsGUI();
	}
	
	private void askQuestionsGUI()
	{
		JOptionPane.showMessageDialog(null, "What is your name?");
		String answerGUI = JOptionPane.showInputDialog("What is your name?");
		answerGUI = JOptionPane.showInputDialog("Why, hello there " + answerGUI + ". How are you today?");
		if(answerGUI.equalsIgnoreCase("good"))
		{
			answerGUI = JOptionPane.showInputDialog("That's nice. What's your favorite color?");
		} else if (answerGUI.equalsIgnoreCase("bad") || answerGUI.equalsIgnoreCase("terrible"))
		{
			answerGUI = JOptionPane.showInputDialog("That's too bad. What's your favorite color?");
		} else
		{
			answerGUI = JOptionPane.showInputDialog("Um ok. What's your favorite color?");
		}
		
	}
	
	private void askQuestions()
	{
		String name;
		String answer;
		System.out.println("What is your name?");
		name = questionScanner.next();
		System.out.println("Why, hello there " + name + ". How are you today?");
		questionScanner.nextLine();
		answer = questionScanner.nextLine();
		if (answer.equalsIgnoreCase("good"))
		{
			System.out.println("That's nice.");
		}
		else if(answer.equalsIgnoreCase("bad")||answer.equalsIgnoreCase("terrible"))
		{
			System.out.println("That's too bad.");
		}
		else
		{
			System.out.println("Um ok.");
		}
		askMoreQuestions(name);
	}
	
	private void askMoreQuestions(String name)
	{
		int age = 0;
		System.out.println("How old are you, " + name + "?");
		age = questionScanner.nextInt();
		questionScanner.nextLine();
		System.out.println("So you are " + age + " years old?");
	}
}
