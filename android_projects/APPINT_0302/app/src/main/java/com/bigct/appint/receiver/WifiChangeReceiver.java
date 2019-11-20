package com.bigct.appint.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.service.AppMonitorService;

public class WifiChangeReceiver extends BroadcastReceiver {
    public WifiChangeReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Service", "----WifiChangeReceiver-----");
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        String ssid = null;
//        int signal = 0;
//        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        Log.d("Service", "----------WifiChangeReceiver----------");
//        if (networkInfo.isConnected()) {
//            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
//            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
//                ssid = connectionInfo.getSSID();
//                signal = connectionInfo.getRssi();
//
//                Handler callback = MobixApplication.getInstance().getWifiChangeHandler();
//                if (callback != null) {
//                    Message msg = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("ssid", ssid);
//                    bundle.putInt("signal", signal);
//                    msg.setData(bundle);
//
//                    callback.sendMessage(msg);
//                }
//            }
//        }
    }
}
