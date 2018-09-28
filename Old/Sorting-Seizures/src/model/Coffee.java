package model;

public class Coffee
{
	private String name;
	private int dark;
	private boolean isCaffeinated;
	
	public Coffee(String name, int dark, boolean isCaffeinated)
	{
		this.name = name;
		this.dark = dark;
		this.isCaffeinated = isCaffeinated;
	}
	public int compareTo(Coffee otherCoffee)
	{
		return 0;
	}
}
