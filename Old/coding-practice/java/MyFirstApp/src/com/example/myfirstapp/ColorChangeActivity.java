package com.example.myfirstapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ColorChangeActivity extends Activity
{
	private Button colorChangeButton;
	private ArrayList<Integer> colorList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		colorChangeButton = (Button) findViewById(R.id.colorChangeButton);
		colorList = new ArrayList<Integer>();
		
		fillTheColorList();
		
		setupListeners();
	}
	
	private void fillTheColorList()
	{
		colorList.add(R.color.black);
		colorList.add(R.color.uglyBrown);
	}
	
	private void setupListeners()
	{
		colorChangeButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View currentView)
			{
				// Button clicked 
				int randomIndex = (int) (Math.random() * colorList.size());
				colorChangeButton.setBackgroundResource(colorList.get(randomIndex));
			}
		});
	}
}
