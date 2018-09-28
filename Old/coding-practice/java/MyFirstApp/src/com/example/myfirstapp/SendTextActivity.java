package com.example.myfirstapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SendTextActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_text, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void OnClick(View currentView)
	{
		try
		{
			String contact = smsNumberField.getText().toString();
			String message = smsMessageField.getText().toString();
			sendSMS(contact, message);
			
			Toast.makeText(currentView.getContext(), "message was sent", Toast.LENGTH_SHORT).show();
		}
		catch(Exception currentException)
		{
			Toast.makeText(currentView.getContext(),  "message was not sent",  Toast.LENGTH_LONG).show();
			Toast.makeText(currentView.getContext(), currentException.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void SendSMS(String messageAddress, String messageContent)
	{
		SmsManager = mySMSManager = SmsManager.getDefault();
		mySMSManager.sendTextMessage(messageAddress, null, messageContent, null, null);
	}
}
