package com.bigct.appint.network.webservice;

import android.content.Context;
import android.text.format.DateFormat;

import com.bigct.appint.Constants;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.AppInfo;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.model.User;
import com.bigct.appint.network.AsyncHttpGet;
import com.bigct.appint.network.AsyncHttpPost;
import com.bigct.appint.service.AppMonitorService;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sdragon on 1/20/2016.
 */
public class WebService
{
    /*
    * First installation
     */
    public static void sendAppInfo(AppInfo appInfo, Context context, final IHttpResponseListener listener)
    {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("phone", appInfo.getPhoneNumber());
            jObj.put("email", appInfo.getEmail());
            jObj.put("os", appInfo.getOs());
            jObj.put("device", appInfo.getDevice());
            jObj.put("network", appInfo.getNetwork());
            jObj.put("imei", appInfo.getImei());
            jObj.put("version", appInfo.getVersion());
            jObj.put("operation", 0);
        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        httpPost(Constants.HTTP_SERVER, jStr, context, false, listener);

        /*
        Everything was ok
            {
            "error": "",
            "token_request": "9874j423987j429",
            "token_response": "423k4j3249",
            "token_validity": "5",
            "operation": "0"
            "auto_refresh_coverage": "1" //Number of seconds to auto-request a scan to get distance
            "auto_refresh_timeout": "30" //Number of seconds after to stop auto-request if the user remains in the coverage
            "mode": "1"
            }
         */
    }

    /*
    * Create an user
     */
    public static void register(User user, AppInfo appInfo, NetInfo netInfo, Context context, final IHttpResponseListener listener)
    {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("fistname", user.getFirstname());
            jObj.put("lastname", user.getLastname());
            jObj.put("phone", user.getPhone());
            jObj.put("email", user.getEmail());
            jObj.put("password", user.getPwd());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("os", appInfo.getOs());
            jObj.put("device", appInfo.getDevice());
            jObj.put("imei", appInfo.getImei());
            if (netInfo == null)
                jObj.put("network", "");
            else
                jObj.put("network", netInfo.getName());
            jObj.put("operation", 1);   // means register request.
        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        httpPost(Constants.HTTP_SERVER, jStr, context, true, listener);

        /*
        Everything was ok
            {
            "UID": "874287364",
            "error": "",
            "token": "423k4j3249",
            "operation": "1"
            }
        There was an error
            {
            "UID": "0",
            "error": "This e-mail already has an account!",
            "token": "423k4j3249",
            "operation": "1"
            }
         */
    }

    /*
    * request login
     */
    public static void login(User user, Context context, boolean showDlg, final IHttpResponseListener listener)
    {
//        AppState.setState(AppState.STATE_LOGINING);
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("email", user.getEmail());
            jObj.put("password", user.getPwd());
            jObj.put("phone", user.getPhone());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("imei", AppInfo.getInstance().getImei());
            jObj.put("operation", 2); // means login request
        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        if (Constants.TEST_MODE) {
            httpGet("http://192.168.0.106/test/123.json", context, listener);
        }
        else {
            httpPost(Constants.HTTP_SERVER, jStr, context, showDlg, listener);
        }

        /*
        Everything was ok
        {
            "UID": "874287364",
            "error": "",
            "token": "423k4j3249",
            "operation": 2,
            "networks": [
                {
                    "uid": "423423",
                    "name": "Building 1",
                    "SSID": "Network_1",
                    "password": "JH7dk",
                    "internet": "0",
                    "ip": "192.168.1.1",
                    "automatic": "0",
                    "command": "http://192.168.1.1/test/v2",
                    "dbm": {
                        "min": "-60",
                        "max": "-50"
                    }
                },
                {
                    "uid": "43428742uda37",
                    "name": "Building 2",
                    "SSID": "Network_2",
                    "password": "432442",
                    "internet": "1",
                    "ip": "192.168.1.1",
                    "automatic": "0",
                    "command": "http://192.168.1.1/test/v2",
                    "dbm": {
                        "min": "-60",
                        "max": "-50"
                        "extended": "-90"
                    }
                }
            ]
        }
        There was an error
        {
            "UID": "0",
            "error": "E-mail and password does not match!",
            "token": "423k4j3249",
            "operation": "2"
        }
         */
    }

    /*
    * Send a Command
     */
    public static void openDoor(User user, NetInfo netInfo, Context context, final IHttpResponseListener listener)
    {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("uid", user.getId());
            jObj.put("email", user.getEmail());
            if (netInfo == null)
                jObj.put("ssid_uid", "");
            else
                jObj.put("ssid_uid", netInfo.getSsid());
            jObj.put("imei", AppInfo.getInstance().getImei());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("operation", 3); // means openDoor request
        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        httpPost(Constants.HTTP_SERVER, jStr, context, true, listener);

        /*
        Everything was ok
        {
            "error": "",
            "token": "423k4j3249",
            "operation": "3"
        }
        */
    }

    /*
    * Recover Password
     */
    public static void recoverPassword(User user, Context context, final IHttpResponseListener listener)
    {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("uid", user.getId());
            jObj.put("email", user.getEmail());
            jObj.put("imei", AppInfo.getInstance().getImei());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("operation", 4);
        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        httpPost(Constants.HTTP_SERVER, jStr, context, true, listener);

        /*
        Everything was ok
            {
            "error": "",
            "token": "423k4j3249",
            "operation": "4",
            "meesage": "There is no account with this email address!",
            }
         */
    }

    /*
    * Change ssid automatic status
     */
    public static void changeAutomatic(User user, NetInfo netInfo, int automatic, Context context, final IHttpResponseListener listener)
    {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("uid", user.getId());
            jObj.put("email", user.getEmail());
            jObj.put("network_uid", netInfo.getUid());
            jObj.put("status", automatic);
            jObj.put("imei", AppInfo.getInstance().getImei());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("operation", 5);

        }
        catch(JSONException e) {
        }

        String jStr = jObj.toString();
        httpPost(Constants.HTTP_SERVER, jStr, context, true, listener);

        /*
        Everything was ok
            {
            "error": "",
            "token": "423k4j3249",
            "operation": "5",
            }
         */
    }

    public static void sendCommand(NetInfo netInfo, Context context, final IHttpResponseListener listener)
    {
        httpGet(netInfo.getOpenCommand(), context, listener);
    }

    private static void httpGet(String url, Context context, final IHttpResponseListener listener)
    {
        AsyncHttpGet req = new AsyncHttpGet(context, new AsyncHttpResponseListenerWrapper(context, listener),
                new ArrayList<NameValuePair>(), false);

        // add log
        ReqLogModel reqLogModel = new ReqLogModel();
        reqLogModel.setRequrl(url);
        reqLogModel.setReqtime(DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString());
        reqLogModel.setParams("");
        try {ReqLogDao.insert(reqLogModel);}catch (SQLException e){}

        req.execute(url);
    }


    public static void httpPost(String url, String jStr, Context context, boolean showDlg, final IHttpResponseListener listener)
    {
        if (context.getClass() == AppMonitorService.class || context.getClass() == MobixApplication.class)
            showDlg = false;

        AsyncHttpPost req = new AsyncHttpPost(context, new AsyncHttpResponseListenerWrapper(context, listener), jStr, showDlg);

        // add log
        ReqLogModel reqLogModel = new ReqLogModel();
        reqLogModel.setRequrl(url);
        reqLogModel.setReqtime(DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString());
        reqLogModel.setParams(jStr);
        try {ReqLogDao.insert(reqLogModel);}catch (SQLException e){}

        req.execute(url);
    }
}
