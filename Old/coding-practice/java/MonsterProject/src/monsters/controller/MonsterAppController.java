package monsters.controller;

import monsters.view.MonsterView;
import monsters.model.MarshmallowMonster;

public class MonsterAppController 
{

	private MonsterView myAppView;
	private MarshmallowMonster monster;
	// private MarshmallowMonster otherMonster;
	
	public MonsterAppController()
	{
		myAppView = new MonsterView(this);
		monster = new MarshmallowMonster("Lil Timmy", //Name
										 2.0, // Legs
										 3.0, // Hair
										 2, // Eye
										 0, // Nose
										 2, // Arm
										 false); // Belly button
		
		
	}
	
	public MarshmallowMonster getMyMonster()
	{
		return monster;
	}
	
	public void start()
	{
		myAppView.displayInformation();
	}
}
