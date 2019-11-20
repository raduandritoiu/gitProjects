package com.bigct.appint.network;

import android.content.Context;
import android.util.Log;

import com.bigct.appint.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.List;

/**
 * AsyncHttpGet makes http get request based on AsyncTask
 * 
 * @author Lemon
 */
public class AsyncHttpGet extends AsyncHttpBase {
	private static final String TAG = "AsyncHttpGet";

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param listener
	 * @param parameters
	 */
	public AsyncHttpGet(Context context, IAsyncHttpResponseListener listener, List<NameValuePair> parameters, boolean isShowWaitingDialog) {
		super(context, listener, parameters, isShowWaitingDialog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fgsecure.meyclub.app.network.AsyncHttpBase#request(java.lang.String)
	 */
	@Override
	protected String request(String url) {
		try {
			HttpParams params = new BasicHttpParams();	
			String combinedParams = "";
			if (!parameters.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : parameters) {
					String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					}
					else {
						combinedParams += paramString;
					}
				}
			}
			HttpConnectionParams.setConnectionTimeout(params, Constants.NETWORK_TIME_OUT);
			HttpConnectionParams.setSoTimeout(params, Constants.NETWORK_TIME_OUT);
			// Lemon commented 19/04/2012
			HttpClient httpclient = createHttpClient(url, params);
//			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url + combinedParams);
			HttpGet httpget1 = new HttpGet(url + combinedParams);
			response = httpclient.execute(httpget1);
			jsonResponse = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			// Lemon added
			// httpclient.getConnectionManager().shutdown();
			statusCode = NETWORK_STATUS_OK;
		}
		catch (Exception e) {
			statusCode = NETWORK_STATUS_ERROR;
			e.printStackTrace();
		}
		return null;
	}
}
