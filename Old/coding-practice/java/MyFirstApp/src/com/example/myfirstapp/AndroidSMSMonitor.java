package com.example.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class AndroidSMSMonitor extends BroadcastReceiver
{
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(intent != null && intent.getAction() != null && ACTION.compareToIgnoreCase(intent.getAction())==0)
		{
			Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
			SmsMessage[] messages = new SmsMessage[pduArray.length];
			// not finished
		}
	}
}
