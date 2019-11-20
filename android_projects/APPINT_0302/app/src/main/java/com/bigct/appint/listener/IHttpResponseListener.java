package com.bigct.appint.listener;

/**
 * Created by sdragon on 1/20/2016.
 */
public interface IHttpResponseListener
{
    // called when success request.
    public void onSuccess(String response);
    // called when failed request.
    public  void onError(String response);
}
