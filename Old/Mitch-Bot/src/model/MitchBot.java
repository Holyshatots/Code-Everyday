package model;

import lejos.nxt.Motor;

public class MitchBot {
	
	private int angle;
	
	public void drawShape(int vertexCount, int length)
	{
		this.angle = calculateAngle(vertexCount);
		for(int drawCount = 0; drawCount < vertexCount; drawCount++)
		{
			try {
				Motor.A.setSpeed(2000);
				Motor.B.setSpeed(2000);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep( length * 1000 );
				Motor.A.stop();
				Motor.B.stop();
			}
			catch(Exception threadException)
			{
				System.out.print(threadException.getMessage());
			}
		}
		
		
	}
	
	private int calculateAngle(int vertexCount)
	{
		int currAngle = 0;
		
		if(vertexCount != 0)
		{
			currAngle = 360/vertexCount;
		}
		return currAngle;
		
	}
	
	public static void main(String args[])
	{
		System.out.print("Hello my name is xXDoritosAndMtnDewXx");
	}
}
