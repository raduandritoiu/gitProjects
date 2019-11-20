package com.bigct.appint.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by sdragon on 2/11/2016.
 */
public class AppInfo {
    String os;
    String device;
    String imei;
    String phoneNumber;
    String email;
    String network;
    String version;

    static AppInfo appInfo;
    public static AppInfo getInstance() {
        if (appInfo == null)
            appInfo = new AppInfo();
        return appInfo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void init(Context context)
    {
        // Device
        device = Build.DEVICE;
        // OS
        os = "Android";

        // IMEI
        String IMEI = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null)
            IMEI = telephonyManager.getDeviceId();
        if (IMEI == null)
            IMEI = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (IMEI == null)
            IMEI = Build.SERIAL;
        imei = IMEI;

        // phone number
        String phone = "";
        if (telephonyManager != null)
            phone = telephonyManager.getLine1Number();
        phoneNumber = phone;

        // gmail
        String gmail = "";
        Pattern gmailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                gmail = account.name;
                break;
            }
        }
        email = gmail;

        // network name
        network = "";
        if (telephonyManager != null)
            network = telephonyManager.getNetworkOperatorName();

        version = Build.VERSION.RELEASE;
    }
}
