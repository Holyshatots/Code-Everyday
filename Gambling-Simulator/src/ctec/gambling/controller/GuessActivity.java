package ctec.gambling.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ctec.gambling.model.AppController;

public class GuessActivity extends Activity {
	
	private Button guessButton;
	private EditText guessInput;
	private TextView playerMoneyLabel;
	private AppController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		controller = (AppController)getApplication();
		
		guessButton = (Button) findViewById(R.id.guessButton);
		guessInput = (EditText) findViewById(R.id.userGuess);
		playerMoneyLabel = (TextView) findViewById(R.id.playerMoneyLabel);
		
		playerMoneyLabel.setText("Your cash: $" + Integer.toString(controller.getPlayerMoney()));
		
		setupListeners();
	}
	
	public void setupListeners()
	{
		guessButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view) {
				if(guessInput.getText().toString().equals(""))
				{
					// Text is blank
					Toast.makeText(getApplicationContext(), "Enter a guess", Toast.LENGTH_SHORT).show();

				}
				else if(Integer.parseInt(guessInput.getText().toString()) > 100 || Integer.parseInt(guessInput.getText().toString()) < 1)
				{
					// Guess not within range
					Toast.makeText(getApplicationContext(), "Must between 1 and 100!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					// Text validated
					controller.setPlayerGuess(Integer.parseInt(guessInput.getText().toString()));
					
					// Reduce the player's money
					controller.addPlayerMoney(-10);
					playerMoneyLabel.setText("Your cash: $" + Integer.toString(controller.getPlayerMoney()));
					guessInput.setText("");
					Intent intent = new Intent(getApplicationContext(), ResultsScreenActivity.class);
					startActivity(intent);
				}
			}
			
		});
	}
}
