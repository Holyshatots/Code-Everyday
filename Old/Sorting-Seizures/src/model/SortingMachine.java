package model;

import java.util.ArrayList;

public class SortingMachine
{
	private long startTime;
	private long endTime;
	private long sortTime;
	
	public long getSortTime()
	{
		return sortTime;
	}
	
	/**
	 * Don't memorize this, just know the generals
	 * @param toBeSorted
	 * @return
	 */
	public int[] selectionSort(int[] toBeSorted)
	{
		int minimum;
		int minimumPosition;
		startTime = System.currentTimeMillis();
		for(int position = 0; position < toBeSorted.length; position++)
		{
			minimumPosition = position;
			minimum = toBeSorted[position];
			for(int next = position +1; next < toBeSorted.length; next++)
			{
				if(toBeSorted[next] < minimum)
				{
					minimum = toBeSorted[next];
					minimumPosition = next;
				}
			}
			if(minimumPosition != position)
			{
				swap(toBeSorted, position, minimumPosition);
			}
		}
		endTime = System.currentTimeMillis();
		
		sortTime = endTime - startTime;
		
		return toBeSorted;
	}
	
	public ArrayList<Integer> selectionSort(ArrayList<Integer> toBeSorted)
	{
		int minimum;
		int minimumPosition;
		startTime = System.currentTimeMillis();
		for(int position = 0; position < toBeSorted.size(); position++)
		{
			minimumPosition = position;
			minimum = toBeSorted.get(position);
			for(int next = position + 1; next < toBeSorted.size(); next++)
			{
				if(toBeSorted.get(next) < minimum)
				{
					minimum = toBeSorted.get(next);
					minimumPosition = next;
				}
			}
			if(minimumPosition != position)
			{
				swap(toBeSorted, position, minimumPosition);
			}
		}
		endTime = System.currentTimeMillis();
		sortTime = endTime - startTime;
		return toBeSorted;
	}
	
	public Coffee[] selectionSort(Coffee[] sortTheCoffees)
	{
		int maximumPosition;
		Coffee maximum;
		startTime = System.currentTimeMillis();
		for(int position = 0; position < sortTheCoffees.length; position++)
		{
			maximumPosition = position;
			maximum = sortTheCoffees[position];
			for(int next = position + 1; next < sortTheCoffees.length; next++)
			{
				if(sortTheCoffees[next].compareTo(maximum) > 0)
				{
					maximum = sortTheCoffees[next];
					maximumPosition = next;
				}
			}
			if(maximumPosition != position)
			{
				swap(sortTheCoffees, position, maximumPosition);
			}
		}
		endTime = System.currentTimeMillis();
		sortTime = endTime - startTime;
		return sortTheCoffees;
	}
	
	public void swap(ArrayList<Integer> array, int position, int change)
	{
		int temp = array.get(position);
		array.set(position, array.get(change));
		array.set(change, temp);
	}
	
	public void swap(Coffee[] array, int position, int change)
	{
		Coffee temp = array[position];
		array[position] = array[change];
		array[change] = temp;	
	}
	
	private void swap(int[] array, int position, int change)
	{
		int temp = array[position];
		array[position] = array[change];
		array[change] = temp;
	}
	
	public String sortingTime(long sortTime)
	{
		String timeToSort = "";
		
		timeToSort += "Days: " + sortTime/(1000*60*60*24) + "\n";
		timeToSort += "Hours: " + sortTime/(1000*60*60) %24 + "\n";
		timeToSort += "Minutes: " + sortTime/(1000*60) % 60 + "\n";
		timeToSort += "Seconds: " + sortTime/(1000) % 60 + "\n";
		timeToSort += "Milliseconds: " + sortTime % 1000 + "\n";
		
		return timeToSort;
	}
}
