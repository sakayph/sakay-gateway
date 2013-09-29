package ph.sakay.gateway;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.os.SystemClock;
import android.util.Log;

public class Scheduler extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("polling_enabled", true)) {
			schedule(context);
		}
	}

	public static void schedule(Context context) {
		Log.d("Scheduler", "Scheduling polling service every 1 minute");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pi = PendingIntent.getService(context, 1, new Intent(context, PollingService.class), 0);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+60000, 60000, pi);
	}

	public static void cancel(Context context) {
		Log.d("Scheduler", "Disabling polling");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pi = PendingIntent.getService(context, 1, new Intent(context, PollingService.class), 0);
		am.cancel(pi);
	}

}
