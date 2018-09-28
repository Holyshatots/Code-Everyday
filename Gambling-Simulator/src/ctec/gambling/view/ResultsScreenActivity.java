package ctec.gambling.view;

import ctec.gambling.controller.R;
import ctec.gambling.model.AppController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultsScreenActivity extends Activity
{
	private Button guessAgainButton;
	private TextView theComputersNumber;
	private TextView playerMoneyLabel;
	private AppController buttzController;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_screen);

		theComputersNumber = (TextView) findViewById(R.id.thecomputersnumber);
		guessAgainButton = (Button) findViewById(R.id.guessButton);
		playerMoneyLabel = (TextView) findViewById(R.id.playerMoneyResultsLabel);

		buttzController = (AppController) getApplication();

		playerMoneyLabel.setText("Your cash: $" + Integer.toString(buttzController.getPlayerMoney()));

		setupListeners();

		theComputersNumber.setText(Integer.toString(buttzController.getComputerGuess()));
	}

	public void setupListeners()
	{
		guessAgainButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				if (buttzController.noCash())
				{
					Intent intent = new Intent(getApplicationContext(), WinActivity.class);
					startActivity(intent);
				}
				finish();
			}

		});
	}
}
