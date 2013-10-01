/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package ph.sakay.gateway;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

public class APIClient {

	private Context mContext;
	private SharedPreferences mPrefs;

	public APIClient(Context context) {
		mContext = context;
		mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
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

	private String getServerKey() {
		return mPrefs.getString("server_key", "");
	}

	private String getServerAddress() {
		return mPrefs.getString("server_url", "https://sms.sakay.ph");
	}

	public HttpResponse get(String endpoint, String queryString) throws Exception {
		HttpGet request = new HttpGet(getServerAddress()+endpoint+queryString);
		request.addHeader("X-API-KEY", getServerKey());
		return getHttpClient().execute(request);
	}

}
