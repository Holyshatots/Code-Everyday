package controller;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import model.Coffee;
import model.SortingMachine;
import view.MainPanel;

public class Controller
{
	MainPanel mainPanel;
	JFrame mainFrame;
	
	private int integers[];
	private ArrayList<Integer> integersList;
	private double[] doubles;
	private String strings[];
	private Coffee[] myCoffees;
	
	private SortingMachine sortingMachine;
	
	public Controller()
	{
		sortingMachine = new SortingMachine();
	}
	public void start()
	{
//		mainPanel = new MainPanel();
//		mainFrame = new JFrame();
//		mainFrame.add(mainPanel);
		
		fillTheArrays();
		System.out.print("Before sorted: ");
		printArray(integers);
		System.out.println("Time before Array: " + sortingMachine.getSortTime());
		sortingMachine.selectionSort(integers);
		System.out.print("After sorted Array: ");
		printArray(integers);
		System.out.println("Sort time: " + sortingMachine.getSortTime());
		System.out.println(sortingMachine.sortingTime(sortingMachine.getSortTime()));
		
		System.out.print("Before sorted: ");
		printArray(integersList);
		System.out.println("Time before Array: " + sortingMachine.getSortTime());
		sortingMachine.selectionSort(integersList);
		System.out.print("After sorted Array: ");
		printArray(integersList);
		System.out.println("Sort time: " + sortingMachine.getSortTime());
		System.out.println(sortingMachine.sortingTime(sortingMachine.getSortTime()));
		
	}
			
	private void printArray(int[] toPrint)
	{
		System.out.print("[");
		for(int i=0; i<toPrint.length; i++)
		{
			System.out.print(toPrint[i]);
			if(i != toPrint.length - 1)
			{
				System.out.print(", ");
			}
		}
		System.out.print("]\n");
	}
	
	private void printArray(ArrayList<Integer> toPrint)
	{
		System.out.print("[");
		for(int i=0; i<toPrint.size(); i++)
		{
			System.out.print(toPrint.get(i));
			if(i != toPrint.size() - 1)
			{
				System.out.print(", ");
			}
		}
		System.out.print("]\n");
	}

	private void fillTheArrays()
	{
		fillTheIntArray(5000);
		fillTheDoubleArray(100);
		fillTheStringArray();
	}
	
	private void fillTheIntArray(int size)
	{
		integers = new int[size];
		integersList = new ArrayList<Integer>(size);
		for(int i = 0; i<integers.length; i++)
		{
			int current = (int) (Math.random() * 36000);
			integers[i] = current;
			integersList.add(current);

		}
		

		
	}
	
	private void fillTheDoubleArray(int size)
	{
		doubles = new double[size];
		for(int i = 0; i<doubles.length; i++)
		{
			doubles[i] = Math.random();
		}
	}
	
	private void fillTheStringArray()
	{
		
	}
	
	private void fillTheCoffeeArray()
	{
		myCoffees = new Coffee[30];
		for(int index = 0; index < myCoffees.length; index++)
		{
			String name = index + " Coffee";
			int dark = (int) (Math.random() * 10);
			boolean isCaffeinated = (dark % 2 == 0);
			myCoffees[index] = new Coffee(name, dark, isCaffeinated);
		}
	}
}
