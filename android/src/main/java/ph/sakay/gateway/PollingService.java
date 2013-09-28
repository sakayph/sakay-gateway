
package ph.sakay.gateway;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

public class PollingService extends IntentService {

	public PollingService() {
		super("PollingService");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		Log.d("PollingService", "poll!");
	}

}
