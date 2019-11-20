package com.bigct.appint.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bigct.appint.Constants;
import com.bigct.appint.MobixApplication;

import java.util.ArrayList;
import java.util.List;

public class WiFiScanReceiver extends BroadcastReceiver {
    private static final String TAG = "WiFiScanReceiver";

    public WiFiScanReceiver() {
        super();
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null)
            return;

        // send message
        Handler callback = MobixApplication.getInstance().getWifiScanResultHandler();
        if (callback != null) {
            Message msg = new Message();
            callback.sendMessage(msg);
        }

        List<ScanResult> results = wifiManager.getScanResults();
        ScanResult bestSignal = null;
        for (ScanResult result : results) {
            if (bestSignal == null || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
                bestSignal = result;
        }
        if (Constants.TEST_MODE) {
            for (ScanResult sr:results) {
                Log.d(TAG, sr.SSID + "-" + sr.BSSID + "-" + sr.level);
            }
        }
        else {
            String message = String.format("%s networks found. %s is the strongest.", results.size(), bestSignal.SSID);
            Log.d(TAG, "onReceive() message: " + message);
        }
    }
}
