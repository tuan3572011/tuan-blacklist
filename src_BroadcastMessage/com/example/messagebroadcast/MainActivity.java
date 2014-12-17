package com.example.messagebroadcast;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	private Button send;
	private final String LOCAL_MESSAGE = "com.example.messagebroadcast.LOCAL_MESSAGE";
	private EditText txtBroadcastType, txtMessage, txtMessages;
	private BroadcastReceiver broadcastReceiver;
	private List<String> messageList;
	private long startAppTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		startAppTime = System.currentTimeMillis();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		send = (Button) findViewById(R.id.btnSend);
		txtBroadcastType = (EditText) findViewById(R.id.txtBroadcastType);
		txtBroadcastType.setText("LOCAL_MESSAGE");
		txtMessage = (EditText) findViewById(R.id.txtMessage);
		txtMessages = (EditText) findViewById(R.id.txtMessages);
		messageList = new ArrayList<String>();

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String intentAction = "com.example.messagebroadcast."
						+ txtBroadcastType.getText().toString();
				String message = txtMessage.getText().toString();
				Intent intent = new Intent(intentAction);
				intent.putExtra("message", message);
				LocalBroadcastManager.getInstance(getApplicationContext())
						.sendBroadcast(intent);
			}
		});

		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String message = intent.getStringExtra("message");
				if (message != null)
					messageList.add("At "
							+ (System.currentTimeMillis() - startAppTime)
							/ 1000 + " second ago: " + message);
				txtMessages.setText("");
				StringBuilder stringBuilder = new StringBuilder();
				for (String ms : messageList) {

					stringBuilder.append(ms);
					stringBuilder.append("\n");
				}
				txtMessages.setText(stringBuilder.toString());
			}
		};

	}

	@Override
	protected void onResume() {
		LocalBroadcastManager.getInstance(this).registerReceiver(
				broadcastReceiver, new IntentFilter(LOCAL_MESSAGE));
		super.onResume();
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				broadcastReceiver);
		super.onPause();
	}

}
