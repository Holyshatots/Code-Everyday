package ctec.gambling.model;

import java.util.Random;

/**
 * You Just Lost.
 * @author Maxwell
 * @author Mitch
 * 
 * 
 */
public class TheGame
{
	private int playerGuess;
	private int computerGuess;
	
	public int computerGuess()
	{
		Random rand = new Random();

		do
		{
			computerGuess = rand.nextInt(101);
		}
		while (computerGuess == playerGuess);

		
		return computerGuess;
	}
	
	/**
	 * Get the computer's "random" guess
	 * @return
	 */
	public int getComputerGuess() {
		return computerGuess;
	}

	/**
	 * Get the player's guess
	 * @return
	 */
	public int getPlayerGuess() {
		return playerGuess;
	}

	/**
	 * Set the player's guess
	 * @param playerGuess
	 */
	public void setPlayerGuess(int playerGuess) {
		this.playerGuess = playerGuess;
	}

}
