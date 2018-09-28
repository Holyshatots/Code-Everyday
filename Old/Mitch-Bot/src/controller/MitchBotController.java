package controller;

import lejos.nxt.Button;
import model.MitchBot;

public class MitchBotController {
	private MitchBot myBot;
	
	public MitchBotController()
	{
		myBot = new MitchBot();
	}
	
	public void start()
	{
		while ( Button.waitForAnyPress() != Button.ID_ESCAPE)
		{
			myBot.drawShape(4,2);
		}
	}
	
}
