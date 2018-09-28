package ctec.gambling.model;

import android.app.Application;

public class AppController extends Application
{
	private TheGame gamblingGame;
	private Player player;
	
	public AppController()
	{
		gamblingGame = new TheGame();
		player = new Player();
		player.setTotalCash(200);
	}
	
	public void setPlayerGuess(int playerGuess)
	{
		gamblingGame.setPlayerGuess(playerGuess);
	}
	
	public int getComputerGuess()
	{
		return gamblingGame.computerGuess();
	}
	
	public void addPlayerMoney(int addMoney)
	{
		player.addCash(addMoney);;
	}
	
	public int getPlayerMoney()
	{
		return player.getTotalCash();
	}
	
	public boolean noCash()
	{
		return player.noCash();
	}
	
}
