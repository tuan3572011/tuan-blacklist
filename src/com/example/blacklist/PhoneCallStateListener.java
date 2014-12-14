package com.example.blacklist;

import java.lang.reflect.Method;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class PhoneCallStateListener extends PhoneStateListener {
	private Context context;

	public PhoneCallStateListener(Context context) {
		this.context = context;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			Toast.makeText(context, incomingNumber, 400).show();
			AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setStreamMute(AudioManager.STREAM_RING, true);
			ITelephony itelephony = getTeleService(context);

			if (itelephony != null) {

				if (incomingNumber.equals("0913569028"))
					itelephony.endCall();
				else
					audioManager.setStreamMute(AudioManager.STREAM_RING, false);
				// audioManager
				// .setStreamVolume(
				// AudioManager.STREAM_RING,
				// audioManager
				// .getStreamMaxVolume(AudioManager.STREAM_RING),
				// 1);
			}
			// TelephonyManager telephonyManager = (TelephonyManager) context
			// .getSystemService(Context.TELEPHONY_SERVICE);

			break;
		}
		super.onCallStateChanged(state, incomingNumber);
	}

	private ITelephony getTeleService(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method m = Class.forName(tm.getClass().getName())
					.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			return (ITelephony) m.invoke(tm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
