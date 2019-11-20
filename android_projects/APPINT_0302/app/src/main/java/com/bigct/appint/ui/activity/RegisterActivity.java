package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
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

public class RegisterActivity extends AppCompatActivity {

    EditText mEtFirtName;
    EditText mEtLastName;
    EditText mEtPassword;
    EditText mEtPhone;
    EditText mEtEmail;
    Button mBtnSignup;
    ImageButton mBtnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtFirtName = (EditText)findViewById(R.id.et_firstname);
        mEtLastName = (EditText)findViewById(R.id.et_lastname);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mEtEmail = (EditText)findViewById(R.id.et_email);
        mEtPhone = (EditText)findViewById(R.id.et_phone);
        mBtnSignup = (Button)findViewById(R.id.btn_signup);
        mBtnClose = (ImageButton)findViewById(R.id.btn_close);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

                String firstname = mEtFirtName.getText().toString();
                String lastname = mEtLastName.getText().toString();
                String pwd = mEtPassword.getText().toString();
                String email = mEtEmail.getText().toString();
                String phone = mEtPhone.getText().toString();

                if (firstname.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please Input First Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lastname.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please Input Last Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please Pasword.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getBaseContext(), "Please Input Valid Email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
                    Toast.makeText(getBaseContext(), "Please Valid Phonenumber.", Toast.LENGTH_SHORT).show();
                    return;
                }


                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setPwd(pwd);
                user.setEmail(email);
                user.setPhone(phone);
//                String phone = MobixApplication.getInstance().getAppInfo().getPhoneNumber();
//                if (phone == null)
//                    phone = "";
//                user.setPhone(phone);

                register(user);
            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
    }

    public void register(User user) {
        WebService.register(user, MobixApplication.getInstance().getAppInfo(), MobixApplication.getInstance().getCrtNetInfo(), this, new IHttpResponseListener() {
            @Override
            public void onSuccess(String response) {
                onRegisterSuccess(response);
            }

            @Override
            public void onError(String response) {
                onRegisterError(response);
            }
        });
    }

    public void onRegisterSuccess(String response) {
        try {
            JSONObject jObj = new JSONObject(response);

            String uid = jObj.getString("UID");
            String error = jObj.getString("error");
            String token = jObj.getString("token");
            String operation = jObj.getString("operation");

            if (uid.isEmpty()) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
            else {
                finish();
            }
        }catch(JSONException e) {

        }
    }

    public void onRegisterError(String response) {
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
    }
}
