package ctec.gambling.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import ctec.gambling.controller.R;
import ctec.gambling.model.AppController;

public class BlackJackActivity extends Activity
{
	private Button hit;
	private Button stay;
	private Button fold;
	private TextView bet;
	private TextView totalMonies;
	private AppController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blackjack);

		controller = new AppController();
		hit = (Button) findViewById(R.id.hit_button);
		stay = (Button) findViewById(R.id.stay_button);
		fold = (Button) findViewById(R.id.fold_button);
		bet = (TextView) findViewById(R.id.playerBet_Label);
		totalMonies = (TextView) findViewById(R.id.playerTotalMonies_Label);

		totalMonies.setText(Integer.toString(controller.getPlayerMoney()));

		setupListeners();

		getBetFromPlayer();
	}

	/**
	 * Go to the winning screen
	 */
	private void goToWinScreen()
	{
		Intent intent = new Intent(getApplicationContext(), WinActivity.class);
		startActivity(intent);
	}

	/**
	 * Display a dialog to get the bet from the player
	 */
	private void getBetFromPlayer()
	{
		PlayerBetDialogFragment betDialog = new PlayerBetDialogFragment();
		betDialog.show(getFragmentManager(), "bet");
	}

	/**
	 * Setup the listeners for the buttons
	 */
	public void setupListeners()
	{
		hit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				// TODO: add card to player hand method here <3 max
			}

		});

		stay.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				// TODO: stay method <3 max
			}

		});

		fold.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				// TODO: fold method <3 max
			}

		});
	}
}
