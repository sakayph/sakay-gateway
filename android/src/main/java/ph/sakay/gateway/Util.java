/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package ph.sakay.gateway;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.PendingIntent;
import android.telephony.SmsManager;

public class Util {

	private static final Pattern leadingCodes = Pattern.compile("^[\\+0]?(63)?");
	private static final Pattern validNumber = Pattern.compile("^9\\d{9}$");

	public static boolean isValidNumber(String number) {
		return validNumber.matcher(number).matches();
	}

	public static String normalizeNumber(String number) {
		return leadingCodes.matcher(number).replaceFirst("");
	}

	public static void sendSms(String target, String body) {
		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> parts = sms.divideMessage(body);
		ArrayList<PendingIntent> sents = new ArrayList<PendingIntent>(parts.size());
		ArrayList<PendingIntent> deliveries = new ArrayList<PendingIntent>(parts.size());
		for(int i = 0; i < parts.size(); ++i) {
			sents.add(null);
			deliveries.add(null);
		}
		sms.sendMultipartTextMessage(target, null, parts, sents, deliveries);
	}
}
