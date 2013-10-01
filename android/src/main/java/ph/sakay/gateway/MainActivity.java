/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package ph.sakay.gateway;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class MainActivity extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);

		findPreference("polling_enabled").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object value) {
				boolean val = (Boolean) value;
				if(val) {
					Scheduler.schedule(MainActivity.this);
				}
				else {
					Scheduler.cancel(MainActivity.this);
				}
				return true;
			}
		});
	}
}
