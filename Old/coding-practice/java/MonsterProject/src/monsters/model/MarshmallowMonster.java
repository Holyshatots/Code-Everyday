package monsters.model;

public class MarshmallowMonster 
{

	// Declaration Section
	private String name;
	private double numberOfLegs;
	private double hairCount;
	private int eyeCount;
	private int noseCount;
	private int armCount;
	private boolean hasBellyButton;
	
	public MarshmallowMonster()
	{
		
	}
	
	public MarshmallowMonster(String name, 
							  double numberOfLegs, 
							  double hairCount, 
							  int eyeCount, 
							  int noseCount, 
							  int armCount, 
							  boolean hasBellyButton)
	{
		this.name = name;
		this.numberOfLegs = numberOfLegs;
		this.hairCount = hairCount;
		this.eyeCount = eyeCount;
		this.noseCount = noseCount;
		this.armCount = armCount;
		this.hasBellyButton = hasBellyButton;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getNumberOfLegs()
	{
		return numberOfLegs;
	}
	
	public double getHairCount()
	{
		return hairCount;
	}
	
	public int getEyeCount()
	{
		return eyeCount;
	}
	
	public int getNoseCount()
	{
		return noseCount;
	}
	
	public int getArmCount()
	{
		return armCount;
	}
	
	public boolean hasBellyButton()
	{
		return hasBellyButton;
	}
}
