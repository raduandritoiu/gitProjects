package com.bigct.appint.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bigct.appint.AppState;
import com.bigct.appint.Constants;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.AppInfo;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.model.User;
import com.bigct.appint.network.webservice.JsonParser;
import com.bigct.appint.network.webservice.WebService;
import com.bigct.appint.util.Preferences;
import com.bigct.appint.util.SoundPlayer;
import com.bigct.appint.util.Util;

import java.util.Date;

public class AppMonitorService extends Service
{
    public static final String SERVICE_ACTION = "com.bigct.appint.service.AppMonitorService";

    public static boolean mIsRunning = false;

    MobixAppProcess mThread;
    Date mLastInstalledDate;
    long mStartRefreshTime;


    public AppMonitorService() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mIsRunning = true;
        mStartRefreshTime = 0;
        mLastInstalledDate = null;

        // init app info
        AppInfo.getInstance().init(this);
        // open preferences.
        Preferences.open(this);
        // open sound player.
        SoundPlayer.open(this);

        // load preference variables.
        // last installed date.
        Log.d("Service", "--------onStartCommand-----------");
        mLastInstalledDate = Preferences.getInstance().getInstalledTime();

        AppState.setState(Preferences.getInstance().getAppState());
        if (AppState.State == AppState.STATE_LOGINING)
            AppState.State = AppState.STATE_LOGIN;
        if (AppState.State == AppState.STATE_INSTALLING)
            AppState.State = AppState.STATE_NONE;

        if (MobixApplication.getInstance().getNetInfoList() == null ||
                MobixApplication.getInstance().getNetInfoList().size() == 0) {
            if (AppState.State > AppState.STATE_LOGIN)
                AppState.State = AppState.STATE_LOGIN;
        }

        // start app process.
        mThread = new MobixAppProcess();
        mThread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("Service", "onDestroy");
        mIsRunning = false;
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        mIsRunning = false;
        super.onTaskRemoved(rootIntent);

        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 5000, restartServicePendingIntent);
    }


    /* install */
    public void doInstall() {
        AppState.setState(AppState.STATE_INSTALLING);
        WebService.sendAppInfo(AppInfo.getInstance(), this, new IHttpResponseListener() {
            @Override
            public void onSuccess(String response) {
                JsonParser.parseInstallResponse(response);
                AppState.setState(AppState.STATE_LOGIN);
            }

            @Override
            public void onError(String response) {
                AppState.setState(AppState.STATE_NONE);
            }
        });
    }


    /* login */
    public void doLogin() {
        // do nothing.
        User user = MobixApplication.getInstance().getUser();
        if (user != null && user.isValid()) {
            if (AppState.State != AppState.STATE_LOGINING) {
                AppState.setState(AppState.STATE_LOGINING);
                WebService.login(user, this, false, new IHttpResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        MobixApplication.getInstance().handleLogin(response);
                    }

                    @Override
                    public void onError(String response) {
                        AppState.setState(AppState.STATE_READY);
                    }
                });
            }
        } else {
            AppState.setState(AppState.STATE_READY);
        }
    }





    /* running logic */
    final long maxScanTimeout = 600000; // 10 minutes in mili
    final long connectingTimeout = 10000; // 10 seconds in mili
    long lastScanTime = 0;
    long lastRefreshTime = 0;



    public void doRunning() {
        NetInfo crtNetInfo = MobixApplication.getInstance().getCrtNetInfo();
        long curTime = System.currentTimeMillis();

        if (crtNetInfo == null) {
            // must update netInfos' signals from wifi - but without scannning again
            // so because the update is onScan callback it is redundant to do this
            // BUUUT lets rescan once every 10 minutes just for funzies (si sa vb cu dumi daca e ok asa)
            if (curTime - lastScanTime > maxScanTimeout) {
                scanForNetworks(curTime);
            }
            // sa vb cu dumi de astea doua cazuri de if
            else if (!MobixApplication.getInstance().isWifiScanned()) {
                scanForNetworks(curTime);
            }
            return;
        }

        // check connected network and update net state
        String connNetSsid = MobixApplication.getInstance().getConnectedNetSsid();
        if (crtNetInfo.getSsid().equals(connNetSsid)) {
            // it is the connected one - may have been all along or may just have been in connecting
            crtNetInfo.connected();
        }
        else {
            crtNetInfo.setIsconnected(false);
        }

        // the current net is connected
        if (crtNetInfo.isconnected()) {
            // it is in range for signal
            if (crtNetInfo.isInDbmRange()) {
                if (crtNetInfo.openDoor()) {
                    WebService.sendCommand(crtNetInfo, this, new IHttpResponseListener() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("======>", "Door Opened");
                        }

                        @Override
                        public void onError(String response) {
                            Log.d("======>", "Door ERROR");
                        }
                    });
                    SoundPlayer.playBeep();
                }
                else {
                    crtNetInfo.disconected();
                    crtNetInfo.makeUnavailable(curTime);
                    MobixApplication.getInstance().setCrtNetInfo(null);
                    scanForNetworks(curTime);
                }
            }
            // is is in extended and timeout
            else if (crtNetInfo.isJustInDbmExtended() && curTime - lastRefreshTime > Constants.AUTO_REFRESH_COVERAGE) {
                scanForNetworks(curTime);
            }
        }
        // the current net is still connecting give it time and we waited enough invalidate it and rescan
        if (crtNetInfo.isconnecting() && curTime - crtNetInfo.getConnectingStartTime() > connectingTimeout) {
            crtNetInfo.disconected();
            scanForNetworks(curTime);
        }

        // here ends up only if is not connecting or connected
    }


    private void scanForNetworks(long crtTime) {
        lastScanTime = crtTime;
        lastRefreshTime = crtTime;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.startScan();
        }
    }



    /* Main logic process thread. */
    private class MobixAppProcess extends Thread
    {
        private int threadSleep = 1000; // 1 sec

        public void run() {
            try {
                while (mIsRunning) {
                    Date now = new Date();
                    int days = Util.getDaysBetween(now, mLastInstalledDate);

                    // install every days.
                    if (AppState.State == AppState.STATE_NONE
                            || ((AppState.State == AppState.STATE_READY || AppState.State == AppState.STATE_LOGOUT || AppState.State == AppState.STATE_RUNNING) && days >= Constants.TOKEN_VALIDITY)) {
                        doInstall();
                        // save last installed date to preferences.
                        mLastInstalledDate = now;
                        Preferences.getInstance().setInstalledTime(mLastInstalledDate);
                    }
                    // if the app is login state, then do login.
                    else if (AppState.State == AppState.STATE_READY || AppState.State == AppState.STATE_LOGIN) {
                        doLogin();
                    }
                    // if the app is running state, then do logic.
                    else if (AppState.State >= AppState.STATE_RUNNING) {
                        doRunning();
                    }

                    // sleep - the timer
                    Thread.sleep(threadSleep);
                }

                // if the service is killed, let this process killed.
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Log.d("------C-----", "Exception");
            }
            Log.d("------T-----", "ThreadEnd");
            return;
        }
    }
}