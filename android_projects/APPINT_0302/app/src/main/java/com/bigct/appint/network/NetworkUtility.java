/*
 * Name: $RCSfile: NetworkUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 3:57:18 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.bigct.appint.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.bigct.appint.Constants;
import com.bigct.appint.model.NetInfo;

/**
 * NetworkUtility checks available network
 * 
 * @author Lemon
 */
public final class NetworkUtility
{
	/** Check network connection */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) {
			return false;
		}
		if (!i.isConnected()) {
			return false;
		}
		if (!i.isAvailable()) {
			return false;
		}
		return true;
	}


	public static void connectToWifi(NetInfo netInfo, Context context) {
        if (netInfo == null)
            return;
        String security = netInfo.getCaps().toLowerCase();
        String ssid = "\"" + netInfo.getSsid() + "\""; //"\"BigCity\""
        String pwd = "\"" + netInfo.getPwd() + "\""; //"\"dachengjishu20150812\"";
        if (Constants.TEST_MODE) {
            ssid = "\"BigCity\"";
            pwd = "\"dachengjishu20150812\"";
        }

        if (Build.VERSION.SDK_INT >= 21) {
            ssid = ssid.replace("\"", "");
        }

        WifiConfiguration wfc = new WifiConfiguration();

        wfc.SSID = ssid;
        wfc.status = WifiConfiguration.Status.DISABLED;
        wfc.priority = 40;

        // WEP networks
        if (security.contains("wep")) {
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

            wfc.wepKeys[0] = pwd;
            wfc.wepTxKeyIndex = 0;
        }
        // WPA/WPA2 networks
        else if (security.contains("wpa") || security.contains("wpa2")) {
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

            wfc.preSharedKey = pwd;
        }
        // OPEN networks
        else {
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedAuthAlgorithms.clear();
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        }

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo == null || !wifiInfo.getSSID().equals(ssid)) {
                int netId = wifiManager.addNetwork(wfc);
                if (netId != -1) {
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();
                }
            }
        }
    }
}
