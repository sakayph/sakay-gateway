package ph.sakay.gateway;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

public class PollingService extends IntentService {

	public PollingService() {
		super("PollingService");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		Log.d("PollingService", "Attempting to poll");
		try {
			HttpResponse response = new APIClient(this).get("/poll", "");
			int status = response.getStatusLine().getStatusCode();
			if(status >= 200 && status < 300) {
				Log.d("PollingService", "Got server response");
				ToSend toSend = new ToSend();
				ObjectMapper m = new ObjectMapper();
				ObjectReader r = m.readerForUpdating(toSend);
				JsonParser p = m.getJsonFactory().createJsonParser(response.getEntity().getContent());

				p.nextToken();
				while(p.nextToken() != JsonToken.END_ARRAY) {
					r.readValue(p);
					Util.sendSms(toSend.target, toSend.message);
				}
			}
			else {
				String body = EntityUtils.toString(response.getEntity());
				Log.e("PollingService", "Error while contacting server. Got status "+status+" and response: "+body);
			}
		}
		catch (Exception e) {
			Log.e("PollingService", "Error while contacting server", e);
		}
	}

}
