package com.bigct.appint.network;

import android.content.Context;
import android.util.Log;

import com.bigct.appint.view.dialog.SimpleProgressDialog;

import org.json.JSONException;

/**
 * AsyncHttpResponseListener: process server response
 * 
 * @author Lemon
 */
public class AsyncHttpResponseListener implements IAsyncHttpResponseListener
{
	private static final String TAG = "AsyncHttpResponseListener";

	private Context context;
	SimpleProgressDialog progressDialog;

	public AsyncHttpResponseListener(Context context) {
		this.context = context;
	}

	@Override
	public void before() {
		// Show waiting dialog during connection
		try {
			progressDialog = new SimpleProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		catch (Exception e) {
			progressDialog = new SimpleProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	}

	@Override
	public void after(int statusCode, String response) {
		switch (statusCode) {
		case AsyncHttpBase.NETWORK_STATUS_OFF:
			try {
				//DialogUtility.alert(context.getParent(), "Network is unavailable");
			}
			catch (Exception e) {
				//DialogUtility.alert(context.getParent(), "Network is unavaiable");
				e.printStackTrace();
			}
            processIfResponseFail();
			break;
		case AsyncHttpBase.NETWORK_STATUS_OK:
			processHttpResponse(response);
			break;
		default:
			try {
				//DialogUtility.alert(context, "Connection Error");
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
//				DialogUtility.alert(context.getParent(), "Connection Error");
			}
            processIfResponseFail();
		}

		try {
			if (progressDialog != null) {
				progressDialog.dismiss();
				progressDialog.cancel();
			}
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Process HttpResponse
	 * 
	 * @param response
	 */
	public void processHttpResponse(String response) {
		try {
			// Get json response
			long current = System.currentTimeMillis();

			if (response == null) {
				//DialogUtility.alert(context, "Can't extract server data");
				return;
			}

			// if (commonInfo.isSuccess()) {
			processIfResponseSuccess(response);
			// } else {
			// processIfResponseFail();
			// context.checkInvalidToken(commonInfo.getMessage());
			// }
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				//DialogUtility.alert(context, "Service error");
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				//DialogUtility.alert(context.getParent(), "Service error");
				e1.printStackTrace();
			}
            processIfResponseFail();
		}
	}

	/**
	 * Interface function
	 * 
	 * @throws JSONException
	 */
	public void processIfResponseSuccess(String response) {
		Log.i(TAG, "Process if response is success ========");
	}

	/**
	 * Interface function
	 */
	public void processIfResponseFail() {
		// SmartLog.log(TAG, "Process if response is fail ===================");
	}
}
