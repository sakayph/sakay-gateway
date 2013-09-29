package ph.sakay.gateway;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class RoutingService extends IntentService {

	public RoutingService() {
		super("RoutingService");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		Log.d("RoutingService", "Got routing request.");
		String sender = intent.getStringExtra("sender");
		String body = intent.getStringExtra("body");

		String reply = request(sender, body);
		Util.sendSms(sender, reply);
	}

	private String getOwnNumber() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getLine1Number();
		if(number == null) number = "unknown";
		return number;
	}

	private String request(String sender, String message) {
		sender = Util.hash(sender);
		try {
			Log.d("RoutingService", "Querying server");
			HttpResponse response = new APIClient(this).get("/sms",
				"?target="+Uri.encode(getOwnNumber())+
				"&body="+Uri.encode(message)+
				"&source="+Uri.encode(sender)
			);
			int status = response.getStatusLine().getStatusCode();
			String body = EntityUtils.toString(response.getEntity());
			if(status >= 200 && status < 300) {
				Log.d("RoutingService", "Got server response: "+body);
				return body;
			}
			else {
				Log.e("RoutingService", "Error while querying server. Got status: "+status+" and response: "+body);
				return "Sorry, there is a problem with the server.";
			}
		}
		catch (Exception e) {
			Log.d("RoutingService", "Error while querying server.", e);
			return "Sorry, an error has occurred.";
		}
	}

}
