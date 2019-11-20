package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigct.appint.AppState;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;

public class SplashActivity extends AppCompatActivity {

    private Thread splashTread;
    protected boolean _active = true;
    protected int _splashTime = 2000;

    boolean mIsLogined;
    boolean mIsInitialized;
    boolean mDownCounted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showSplashScreen();

        startInitLoading();
    }

    private void showSplashScreen() {
        mDownCounted = false;
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
//                        if (AppState.State == AppState.STATE_READY || AppState.State == AppState.STATE_LOGOUT || AppState.State == AppState.STATE_RUNNING)
//                            break;
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    mDownCounted = true;

                    try {
                        while (!mIsInitialized) {
                            sleep(100);
                        }
                    }catch (InterruptedException e) {

                    }

                    try {
                        while (true) {
                            if (AppState.State == AppState.STATE_READY || AppState.State == AppState.STATE_LOGOUT || AppState.State == AppState.STATE_RUNNING)
                                break;
                            sleep(100);
                        }
                    }catch (InterruptedException e){}
                    go();

                    interrupt();
                }
            }
        };
        splashTread.start();
    }

    public void go() {
        if (!mIsInitialized || !mDownCounted)
            return;

        if (AppState.State == AppState.STATE_READY || AppState.State == AppState.STATE_LOGOUT)
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, MainActivity.class));

//        if (mIsLogined) {
//            startActivity(new Intent(getBaseContext(), MainActivity.class));
//        }
//        else {
//            startActivity(new Intent(getBaseContext(), LoginActivity.class));
//        }
        finish();
    }

    private void startInitLoading() {
        mIsInitialized = false;

//        User user = MobixApplication.getInstance().getUser();
//
//        if (user != null && user.isValid()) {
//            WebService.login(user, this, false, new IHttpResponseListener() {
//                @Override
//                public void onSuccess(String response) { onLoginSuccess(response); }
//
//                @Override
//                public void onError(String response) { onLoginError(response); }
//            });
//        }
//        else
        {
            mIsInitialized = true;
        }
    }

    void onLoginSuccess(String response) {
        if (MobixApplication.getInstance().handleLogin(response)) {
            mIsLogined = true;
        }

        mIsInitialized = true;
    }

    void onLoginError(String response) {
        AppState.setState(AppState.STATE_READY);
        mIsInitialized = true;
    }
}
