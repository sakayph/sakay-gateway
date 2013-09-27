package ph.sakay.gateway;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;

public class MainActivity extends PreferenceActivity {

	private IntentFilter mFilter;
	private SmsReceiver mReceiver;
	private Preference mButton;
	private boolean mEnabled;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);
		mFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		mFilter.setPriority(999);
		mReceiver = new SmsReceiver();

		mButton = findPreference("button");
		mButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference p) {
				if(mEnabled) {
					unregisterReceiver(mReceiver);
					mButton.setTitle("Start");
				}
				else {
					registerReceiver(mReceiver, mFilter);
					mButton.setTitle("Stop");
				}
				mEnabled = !mEnabled;
				return true;
			}
		});
	}
}
