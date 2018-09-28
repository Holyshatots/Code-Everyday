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

public class GambleActivity extends Activity
{
	private Button toGuesser;
	private Button toBlackJack;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamble);

		toGuesser = (Button) findViewById(R.id.to_simulator_button);
		toBlackJack = (Button) findViewById(R.id.to_blackjack_button);

		setupListeners();
	}

	/**
	 * Setup the listeners for the buttons
	 */
	public void setupListeners()
	{
		toGuesser.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
				startActivity(intent);
			}
		});

		toBlackJack.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				Intent intent = new Intent(getApplicationContext(), BlackJackActivity.class);
				startActivity(intent);
			}

		});
	}
}
