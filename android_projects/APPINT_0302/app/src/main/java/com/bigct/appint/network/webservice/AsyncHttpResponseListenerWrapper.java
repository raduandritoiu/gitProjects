package com.bigct.appint.network.webservice;

import android.content.Context;

import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.network.AsyncHttpResponseListener;

/**
 * Created by radua on 29.03.2016.
 */
public class AsyncHttpResponseListenerWrapper extends AsyncHttpResponseListener
{
    IHttpResponseListener listener;
    public AsyncHttpResponseListenerWrapper(Context context, IHttpResponseListener nlistener) {
        super(context);
        listener = nlistener;
    }

    @Override
    public void processIfResponseSuccess(String response) {
        if (response != null) {
            if (listener != null)
                listener.onSuccess(response);
        }
        else {
            if (listener != null)
                listener.onError(response);
        }
    }

    @Override
    public void processIfResponseFail() {
        if (listener != null)
            listener.onError("network or data error");
    }
}
