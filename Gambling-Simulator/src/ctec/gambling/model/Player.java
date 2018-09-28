package ctec.gambling.model;

public class Player
{
	private String name;
	private int totalCash;
	private Hand hand;

	public Player()
	{
		this.hand = null;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setHand(Hand h)
	{
		this.hand = h;
	}

	public void getHand()
	{
		hand.showHand();
	}

	public int getTotalCash()
	{
		return totalCash;
	}

	public void setTotalCash(int totalCash)
	{
		this.totalCash = totalCash;
	}
	
	public boolean noCash()
	{
		if(this.totalCash == 0)
		{
			return true;
		}
		
		return false;
	}
	
	public void addCash(int add)
	{
		this.totalCash += add;
	}
}
