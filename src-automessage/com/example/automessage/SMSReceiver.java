package com.example.automessage;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.example.automessage.SinhVien_Diem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	List<SinhVien_Diem> ids = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		ids = loadData();
		if (intent != null) {
			if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent
					.getAction())) {
				SmsManager sms = SmsManager.getDefault();
				Bundle bundle = intent.getExtras();
				List<SmsMessage> smsMessages = new ArrayList<SmsMessage>();
				Toast.makeText(context, "check bundle", 400).show();
				if (bundle != null) {
					Toast.makeText(context, "bundle not null", 400).show();
					Object[] pdus = (Object[]) bundle.get("pdus");
					Toast.makeText(context, "so message: " + pdus.length, 400)
							.show();

					for (Object pdu : pdus) {
						SmsMessage smsMessage = SmsMessage
								.createFromPdu((byte[]) pdu);
						smsMessages.add(smsMessage);
					}
					for (SmsMessage smsMessage : smsMessages) {
						String messageNum = smsMessage.getOriginatingAddress();
						Toast.makeText(context, messageNum, 400).show();
						String incomingMessageBody = smsMessage
								.getMessageBody();
						Toast.makeText(context, incomingMessageBody, 400)
								.show();
						SinhVien_Diem ketQua = isValidSyntax(
								incomingMessageBody, ids);
						if (ketQua != null)
							Toast.makeText(context, ketQua.toString(), 400)
									.show();
						String messageReturn = "";
						if (ketQua != null)
							messageReturn = ketQua.toString();
						else
							messageReturn = "Cu phap khong hop le";
						Toast.makeText(context, messageReturn, 400).show();
						sms.sendTextMessage(messageNum, null, messageReturn,
								null, null);
					}
				}
			}
		}
	}

	private SinhVien_Diem isValidSyntax(String messageBody,
			List<SinhVien_Diem> ids) {
		if (ids != null)
			if (!ids.isEmpty()) {
				StringTokenizer token = new StringTokenizer(messageBody);
				if (token.countTokens() == 2) {
					String syntaxDiem = token.nextToken();
					String mssv = token.nextToken();
					if (!syntaxDiem.equalsIgnoreCase("DiemThi"))
						return null;
					for (SinhVien_Diem sv_diem : ids) {
						if (sv_diem.getMssv().equals(mssv))
							return sv_diem;
					}
				}
			}
		return null;
	}

	private List<SinhVien_Diem> loadData() {
		List<SinhVien_Diem> st = new ArrayList<SinhVien_Diem>();
		st.add(new SinhVien_Diem("11130046", 7.5, 5.5, 4.5));
		return st;
	}

}
