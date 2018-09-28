package model;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class PilotBot {
	
	final static float WHEEL_DIAMETER = 0.0f;
	final static float TRACK_WIDTH = 0.0f;
	
	private DifferentialPilot pilot;
	
	public PilotBot()
	{
		pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.B, true);
		pilot.setTravelSpeed(30);
	}
	
	public void start()
	{
		
	}
	
	public void setTravelSpeed(float distance)
	{
		pilot.setTravelSpeed(distance);
	}
	
	public void drawEquilateralTriangle(int sideLength)
	{
		for(int i=0; i<3; i++)
		{
			pilot.travel(sideLength);
			pilot.rotate(-60);
		}

	}
	
	public void drawSquare(int length, int width)
	{
		for (int i=0; i<2; i++)
		{
			pilot.travel(length);
			pilot.rotate(-90);
			pilot.travel(width);
			pilot.rotate(-90);
		}

	}
	
	public void drawCircle(int radius)
	{
		pilot.arc(radius, 360);
	}
}
