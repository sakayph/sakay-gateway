/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package ph.sakay.gateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if(!prefs.getBoolean("enabled", true)) {
			return;
		}

		Bundle b = intent.getExtras();
		SmsMessage[] msgs = null;
		String body = new String();
		String sender = null;

		if(b != null) {
			Object[] pdus = (Object[])b.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0; i < msgs.length; ++i){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				body += msgs[i].getMessageBody().toString();
				sender = msgs[i].getOriginatingAddress();
			}

			Log.d("SmsReceiver", "Got message: "+body+", from: "+sender);

			sender = Util.normalizeNumber(sender);
			if(!Util.isValidNumber(sender)) {
				Log.d("SmsReceiver", "Ignoring "+sender+" because number is not valid");
				return;
			}

			Intent i = new Intent(context, RoutingService.class);
			i.putExtra("sender", sender);
			i.putExtra("body", body);
			context.startService(i);
		}
	}

}
