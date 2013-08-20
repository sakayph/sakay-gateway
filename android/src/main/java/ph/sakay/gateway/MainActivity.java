package ph.sakay.gateway;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private IntentFilter mFilter;
	private SmsReceiver mReceiver;
	private Button mButton;
	private boolean mEnabled;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		mFilter.setPriority(999);
		mReceiver = new SmsReceiver();

		mButton = new Button(this);
		mButton.setText("Start");
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mEnabled) {
					unregisterReceiver(mReceiver);
					mButton.setText("Start");
				}
				else {
					registerReceiver(mReceiver, mFilter);
					mButton.setText("Stop");
				}
				mEnabled = !mEnabled;
			}
		});

		setContentView(mButton);
	}
}
