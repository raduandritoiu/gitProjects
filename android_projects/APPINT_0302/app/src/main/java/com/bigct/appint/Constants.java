package com.bigct.appint;

import com.bigct.appint.model.AppInfo;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.model.User;

public class Constants
{
    public static final String HTTP_SERVER = "http://www.pointit.ro/mobile-api/index-old.php";//http://www.pointit.ro/mobile-api/index.php";
    //http://www.pointit.ro/mobile-api/ssid/<user_uid>/<ssid_uid>/IMEI"
    public static final String WEBVIEW_SSID = "http://www.pointit.ro/mobile-api/ssid/%s/%s/%s";
    public static final String WEBVIEW_MEMBERSHIP = "http://www.pointit.ro/mobile-api/membership/%s/%s/%s";
    public static final String WEBVIEW_NOTIFICATIONS = "http://www.pointit.ro/mobile-api/notifications/%s/%s/%s";
    public static final String WEBVIEW_SETTINGS = "http://www.pointit.ro/mobile-api/settings/%s/%s/%s";
    public static final String WEBVIEW_SUPPORT = "http://www.pointit.ro/mobile-api/support/%s/%s/%s";

    public static final int NETWORK_TIME_OUT = 8000; // 5 seconds

    public static String TOKEN1 = "9874j423987j429"; // token request. A key to use to validate all the request from the Webserver
    public static String TOKEN2 = "423k4j3249";      // token response. A key to use to validate all the responses from the Webserver
    public static int TOKEN_VALIDITY = 1;            // token validity. Number of days tokens will be available. After that need to request another ones

    public static long AUTO_REFRESH_COVERAGE = 2000;   // 2 seconds in mili
    public static long AUTO_REFRESH_TIMEOUT = 30000;   // 30 seconds in mili

    public static final boolean TEST_MODE = false;


    public static String getSsidUrl(NetInfo netInfo) {
        User user = MobixApplication.getInstance().getUser();
        String ssid = "";
        String phone = "";
        String uid = "";
        if (user != null) {
            uid = user.getId();
//            phone = user.getPhone();
        }
        if (netInfo != null) {
            ssid = netInfo.getSsid();
        }
        phone = AppInfo.getInstance().getImei();

        String url = String.format(Constants.WEBVIEW_SSID, uid, ssid, phone);
        return url;
    }


    public static String getMembershipUrl() {
        User user = MobixApplication.getInstance().getUser();
        NetInfo netInfo = MobixApplication.getInstance().getCrtNetInfo();
        String ssid = "";
        String phone = "";
        String uid = "";
        if (user != null) {
            uid = user.getId();
//            phone = user.getPhone();
        }
        if (netInfo != null) {
            ssid = netInfo.getSsid();
        }

        phone = AppInfo.getInstance().getImei();

        String url = String.format(Constants.WEBVIEW_MEMBERSHIP, uid, ssid, phone);
        return url;
    }


    public static String getNotificastionsUrl() {
        User user = MobixApplication.getInstance().getUser();
        NetInfo netInfo = MobixApplication.getInstance().getCrtNetInfo();
        String ssid = "";
        String phone = "";
        String uid = "";
        if (user != null) {
            uid = user.getId();
//            phone = user.getPhone();
        }
        if (netInfo != null) {
            ssid = netInfo.getSsid();
        }

        phone = AppInfo.getInstance().getImei();

        String url = String.format(Constants.WEBVIEW_NOTIFICATIONS, uid, ssid, phone);
        return url;
    }


    public static String getSettingsUrl() {
        User user = MobixApplication.getInstance().getUser();
        NetInfo netInfo = MobixApplication.getInstance().getCrtNetInfo();
        String ssid = "";
        String phone = "";
        String uid = "";
        if (user != null) {
            uid = user.getId();
//            phone = user.getPhone();
        }
        if (netInfo != null) {
            ssid = netInfo.getSsid();
        }

        phone = AppInfo.getInstance().getImei();

        String url = String.format(Constants.WEBVIEW_SETTINGS, uid, ssid, phone);
        return url;
    }


    public static String getSupportUrl() {
        User user = MobixApplication.getInstance().getUser();
        NetInfo netInfo = MobixApplication.getInstance().getCrtNetInfo();
        String ssid = "";
        String phone = "";
        String uid = "";
        if (user != null) {
            uid = user.getId();
//            phone = user.getPhone();
        }
        if (netInfo != null) {
            ssid = netInfo.getSsid();
        }

        phone = AppInfo.getInstance().getImei();

        String url = String.format(Constants.WEBVIEW_SUPPORT, uid, ssid, phone);
        return url;
    }
}
