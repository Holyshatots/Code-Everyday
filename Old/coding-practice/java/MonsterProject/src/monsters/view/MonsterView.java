package monsters.view;

import javax.swing.JOptionPane;

import monsters.controller.MonsterAppController;

public class MonsterView 
{
	private MonsterAppController baseController;
	
	public MonsterView(MonsterAppController baseController)
	{
		this.baseController = baseController;
	}
	
	public void displayInformation()
	{
		JOptionPane.showMessageDialog(null, "I made a monster");
		JOptionPane.showMessageDialog(null, "It's name is " + baseController.getMyMonster().getName());
		JOptionPane.showMessageDialog(null, "It has " + baseController.getMyMonster().getArmCount() + " arms and " + baseController.getMyMonster().getEyeCount() + " eyes.");
		if(baseController.getMyMonster().hasBellyButton())
		{
			JOptionPane.showMessageDialog(null, "It has a belly button");
		} else
		{
			JOptionPane.showMessageDialog(null, "It doesn't have a belly button");
		}
	}
}
