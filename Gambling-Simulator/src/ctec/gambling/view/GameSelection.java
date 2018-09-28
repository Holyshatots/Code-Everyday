package ctec.gambling.view;

import ctec.gambling.controller.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activity to select the game the player wants to play
 * 
 * @author Mitch
 * @author Max
 *
 */
public class GameSelection extends Activity
{

	private Button toBlackjackButton;
	private Button toGuessButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_selection);

		toBlackjackButton = (Button) findViewById(R.id.to_blackjack_button);
		toGuessButton = (Button) findViewById(R.id.to_simulator_button);

		setupListeners();
	}

	/**
	 * Setup the class listeners
	 */
	public void setupListeners()
	{
		toBlackjackButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				// Go to blackjack game
				Intent intent = new Intent(getApplicationContext(), BlackJackActivity.class);
				startActivity(intent);
			}

		});

		toGuessButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				// Go to guess game
				Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
				startActivity(intent);
			}

		});
	}
}
