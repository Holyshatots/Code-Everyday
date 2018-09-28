package ctec.gambling.view;

import ctec.gambling.controller.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WinActivity extends Activity
{

	private Button tryAgainButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);

		tryAgainButton = (Button) findViewById(R.id.tryAgainButton);

		setupListeners();
	}

	public void setupListeners()
	{
		tryAgainButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// Intent intent = new Intent(getApplicationContext(),
				// GamblingAddictionActivity.class);
				// startActivity(intent);
				Uri uri = Uri.parse("http://www.helpguide.org/articles/addiction/gambling-addiction-and-problem-gambling.htm");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}

		});
	}
}
