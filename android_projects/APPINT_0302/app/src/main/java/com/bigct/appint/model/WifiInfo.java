package com.bigct.appint.model;

import android.net.wifi.ScanResult;

/**
 * Created by sdragon on 1/20/2016.
 */
public class WifiInfo {
    String ssid;    //wifi ssid
    int signal;  //signal strength in dBm
    String capability;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public static WifiInfo from(ScanResult sr) {
        WifiInfo wifiInfo = new WifiInfo();
        wifiInfo.setSsid(sr.SSID);
        wifiInfo.setSignal(sr.level);
        return wifiInfo;
    }
}
