package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigct.appint.Constants;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.model.User;
import com.bigct.appint.network.NetworkUtility;
import com.bigct.appint.network.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WifiInfoDetailActivity extends AppCompatActivity {
    static final String TAG = "WifiInfoDetailActivity";
    NetInfo mNetInfo;

    TextView mTvSignal;
    TextView mTvUrl;
    EditText mEtHost;
    EditText mEtParams;
    Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info_detail);

        readIntentParams();

        mTvSignal = (TextView)findViewById(R.id.tv_signal);
        mTvUrl = (TextView)findViewById(R.id.tv_url);
        mEtHost = (EditText)findViewById(R.id.et_host);
        mEtParams = (EditText)findViewById(R.id.et_params);
        mBtnSubmit = (Button)findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWifiStatusSubmit();
            }
        });

        mTvSignal.setText("SSID: " + mNetInfo.getSsid() + "\n Signal Strength: " + mNetInfo.getSignal() + " dBm");

        connectWifi();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobixApplication.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        MobixApplication.getInstance().awayCurrentActivity(this);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        MobixApplication.getInstance().awayCurrentActivity(this);

        super.onPause();
    }

    void readIntentParams() {
        Intent intent = getIntent();

        String ssid = intent.getStringExtra("wifi_ssid");
        int signal = intent.getIntExtra("wifi_signal", 0);

        List<NetInfo> netInfoList = MobixApplication.getInstance().getNetInfoList();

        for (NetInfo netInfo:netInfoList) {
            if (netInfo.getSsid().equals(ssid)) {
                mNetInfo = netInfo;
                break;
            }
        }
    }

    void onWifiStatusSubmit() {
        String host = mEtHost.getText().toString();
        String params = mEtParams.getText().toString();

        String url = "http://"+host+"/"+params;
        mTvUrl.setText(url);

        User user = MobixApplication.getInstance().getUser();
        NetInfo netInfo = mNetInfo;
        JSONObject jObj = new JSONObject();

        try {
            jObj.put("uid", user.getId());
            jObj.put("email", user.getEmail());
            jObj.put("ssid_uid", netInfo.getSsid());
            jObj.put("token", Constants.TOKEN1);
            jObj.put("operation", 3);               // means openDoor request
        }catch(JSONException e) {

        }

        String jStr = jObj.toString(); // "{'id':"+id+",'pwd':"+pwd}"

        WebService.httpPost(url, jStr, this, true, new IHttpResponseListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String response) {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });

//        WebService.openDoor(MobixApplication.getInstance().getUser(), mNetInfo, this, new IHttpResponseListener() {
//            @Override
//            public void onSuccess(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(String response) {
//                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    void connectWifi() {
        NetworkUtility.connectToWifi(mNetInfo, getApplicationContext());
    }

    public void fireNetInfoListChanged(List<NetInfo> netInfoList) {
        if (mTvSignal != null) {
            mTvSignal.setText("SSID: " + mNetInfo.getSsid() + "\n Signal Strength: " + mNetInfo.getSignal() + " dBm");
        }
    }
}
