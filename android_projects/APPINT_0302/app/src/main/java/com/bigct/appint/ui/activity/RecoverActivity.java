package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.User;
import com.bigct.appint.network.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class RecoverActivity extends AppCompatActivity {

    EditText mEtEmail;
    Button mBtnRecover;
    ImageButton mBtnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        mEtEmail = (EditText)findViewById(R.id.et_email);
        mBtnRecover = (Button)findViewById(R.id.btn_recover);
        mBtnClose = (ImageButton)findViewById(R.id.btn_close);

        mBtnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

                String email = mEtEmail.getText().toString();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(getBaseContext(), "Please Input Valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setEmail(mEtEmail.getText().toString());
                String phone = MobixApplication.getInstance().getAppInfo().getPhoneNumber();
                if (phone == null)
                    phone = "";
                user.setPhone(phone);

                recover(user);
            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
    }

    public void recover(User user) {
        WebService.recoverPassword(user, this, new IHttpResponseListener() {
            @Override
            public void onSuccess(String response) {
                onRecoverSuccess(response);
            }

            @Override
            public void onError(String response) {
                onRecoverError(response);
            }
        });
    }

    public void onRecoverSuccess(String response) {
        try {
            JSONObject jObj = new JSONObject(response);

            String error = jObj.getString("error");
            String token = jObj.getString("token");
            String operation = jObj.getString("operation");
            String message = jObj.getString("message");

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }catch(JSONException e) {

        }
    }

    public void onRecoverError(String response) {
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
    }
}
