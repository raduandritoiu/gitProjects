package com.bigct.appint.network.webservice;

import com.bigct.appint.Constants;
import com.bigct.appint.model.LoginErr;
import com.bigct.appint.model.NetInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonParser {
    public static String parseLoginReponse(String jResponse, List<NetInfo> netInfoList, LoginErr loginErr)
    {
        String uid="";
        try {
            JSONObject jObj = new JSONObject(jResponse);
            uid = jObj.getString("UID");
            String error = jObj.getString("error");
            String token = jObj.getString("token");
            JSONArray jWifis = jObj.getJSONArray("networks");
            String operation = jObj.getString("operation");

            if (uid.isEmpty()) {
                loginErr.setUid(uid);
                loginErr.setError(error);
                loginErr.setToken(token);
                loginErr.setOperation(operation);
            }
            else {
                for (int i = 0; i < jWifis.length(); i++) {
                    JSONObject jNet = jWifis.getJSONObject(i);
                    NetInfo netInfo = new NetInfo();
                    netInfo.setUid(jNet.getString("uid"));
                    netInfo.setName(jNet.getString("name"));
                    netInfo.setSsid(jNet.getString("SSID"));
                    netInfo.setPwd(jNet.getString("password"));
                    netInfo.setInternet(jNet.getInt("internet"));
                    netInfo.setIp(jNet.getString("ip"));
                    netInfo.setAutomatic(jNet.getInt("automatic"));
                    netInfo.setOpenCommand(jNet.getString("command"));
                    try {
                        JSONObject jDbm = jNet.getJSONObject("dbm");
                        netInfo.setDbmMin(jDbm.getInt("min"));
                        netInfo.setDbmMax(jDbm.getInt("max"));
                        netInfo.setDbmExtended(jDbm.getInt("extended"));
                    }
                    catch (JSONException e){
                    }
                    netInfoList.add(netInfo);
                }
            }
        }
        catch(JSONException e) {
        }
        return uid;
    }

    public static String parseInstallResponse(String jResponse)
    {
        String err = "";
        try {
            // parse response
            JSONObject jObj = new JSONObject(jResponse);
            err = jObj.getString("error");
            String token_request = jObj.getString("token_request");
            String token_response = jObj.getString("token_response");
            String token_validity = jObj.getString("token_validity");
            String operation = jObj.getString("operation");
            String auto_refresh_coverage = jObj.getString("auto_refresh_coverage");
            String auto_refresh_timeout = jObj.getString("auto_refresh_timeout");
            String mode = jObj.getString("mode");

            // set variables.
            Constants.TOKEN1 = token_request;
            Constants.TOKEN2 = token_response;
            Constants.TOKEN_VALIDITY = Integer.valueOf(token_validity);
            Constants.AUTO_REFRESH_TIMEOUT = Integer.valueOf(auto_refresh_timeout) * 1000;
            Constants.AUTO_REFRESH_COVERAGE = Integer.valueOf(auto_refresh_coverage) * 1000;
        }
        catch(JSONException e) {
        }
        return err;
    }
}
