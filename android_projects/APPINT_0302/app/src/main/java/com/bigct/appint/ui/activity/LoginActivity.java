package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigct.appint.AppState;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.User;
import com.bigct.appint.network.webservice.WebService;
import com.bigct.appint.util.Preferences;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    Button mBtnSignin;
    TextView mTvSignup;
    TextView mTvFogotPwd;
    EditText mTvPwd;
    EditText mTvEmail;

    User mUser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnSignin = (Button)findViewById(R.id.btn_signin);
        mTvSignup = (TextView)findViewById(R.id.tv_signup);
        mTvFogotPwd = (TextView)findViewById(R.id.tv_fogotpwd);
        mTvPwd = (EditText)findViewById(R.id.et_pwd);
        mTvEmail = (EditText)findViewById(R.id.et_email);

        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mTvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        mTvFogotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPwd();
            }
        });

        mTvEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    mTvPwd.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mTvPwd.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    public void login() {
        if (!makeUser())
            return;

        // email & pwd
        AppState.setState(AppState.STATE_LOGINING);
        WebService.login(mUser, this, true, new IHttpResponseListener() {
            @Override
            public void onSuccess(String response) {
                onLoginSuccess(response);
            }

            @Override
            public void onError(String response) {
                onLoginError(response);
            }
        });
    }


    public void register() {
        startActivity(new Intent(getBaseContext(), RegisterActivity.class));
    }

    public void forgotPwd() {
        startActivity(new Intent(getBaseContext(), RecoverActivity.class));
        finish();
    }

    public boolean makeUser() {
        String email="";
        String pwd="";

        if (mTvEmail != null)
            email = mTvEmail.getText().toString();
        if (mTvPwd != null)
            pwd = mTvPwd.getText().toString();

        // validate
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please Input Valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pwd.isEmpty()) {
            Toast.makeText(this, "Please input password.", Toast.LENGTH_SHORT).show();
            return false;
        }

        mUser.setEmail(email);
        mUser.setPwd(pwd);
        String phone = MobixApplication.getInstance().getAppInfo().getPhoneNumber();
        if (phone == null)
            phone = "";
        mUser.setPhone(phone);
        return  true;
    }

    public void onLoginSuccess(String response) {
        if (MobixApplication.getInstance().handleLogin(response)) {
            User user = MobixApplication.getInstance().getUser();
            user.setEmail(mUser.getEmail());
            user.setPwd(mUser.getPwd());
            Preferences.getInstance().setUser(user);
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    public void onLoginError(String response) {
        AppState.setState(AppState.STATE_READY);
        Log.e(TAG, "login failed");
    }

}
