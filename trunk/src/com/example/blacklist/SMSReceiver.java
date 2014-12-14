package com.example.blacklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null) {
			if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent
					.getAction())) {
				// this.abortBroadcast();
				Bundle bundle = intent.getExtras();
				SmsMessage[] msgs = null;
				String str = "";
				Toast.makeText(context, "check bundle", 400).show();
				if (bundle != null) {
					Toast.makeText(context, "bundle not null", 400).show();
					Object[] pdus = (Object[]) bundle.get("pdus");
					Toast.makeText(context, "so message: " + pdus.length, 400)
							.show();
					msgs = new SmsMessage[pdus.length];
					for (int i = 0; i < msgs.length; i++) {
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						String messageNum = msgs[i].getOriginatingAddress();
						Toast.makeText(context, messageNum, 400).show();
						if (messageNum.equals("+841632570179"))
							this.abortBroadcast();
						str += "SMS from " + messageNum;
						str += " :";
						str += msgs[i].getMessageBody().toString();
						str += "\n";

					}
				}
			}
		}
	}

}
