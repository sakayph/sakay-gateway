package ph.sakay.gateway;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
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

		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> parts = sms.divideMessage(reply);
		ArrayList<PendingIntent> sents = new ArrayList<PendingIntent>(parts.size());
		ArrayList<PendingIntent> deliveries = new ArrayList<PendingIntent>(parts.size());
		for(int i = 0; i < parts.size(); ++i) {
			sents.add(null);
			deliveries.add(null);
		}
		sms.sendMultipartTextMessage(sender, null, parts, sents, deliveries);
	}

	private HttpClient getHttpClient() {
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 300000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(1, false));
		return client;
	}

	private String getServerAddress() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		return prefs.getString("server_url", "https://sms.sakay.ph");
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
			HttpGet request = new HttpGet(
				getServerAddress()+"/sms"+
				"?target="+Uri.encode(getOwnNumber())+
				"&body="+Uri.encode(message)+
				"&source="+Uri.encode(sender)
			);
			HttpResponse response = getHttpClient().execute(request);
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
