package com.bigct.appint;

import android.util.Log;

import com.bigct.appint.util.Preferences;

/**
 * Created by sdragon on 2/27/2016.
 */
public class AppState {
    public static final int STATE_NONE = 0;             // must to insall.
    public static final int STATE_INSTALLING = 1;       // now installing.
    public static final int STATE_READY = 2;            // installed or login failed.
    public static final int STATE_LOGOUT = 3;           // must to login. state after installed
    public static final int STATE_LOGIN = 4;            // must to login. state after installed
    public static final int STATE_LOGINING = 5;         // now logining and retrieving user data. state after login.
    public static final int STATE_BG_LOGINING = 6;         // now background logining and retrieving user data. state after login.
    public static final int STATE_RUNNING = 6;          // now running. state after logined.

    public static int State = STATE_NONE;

    public static boolean setState(int state) {
//        if (Preferences.getInstance() != null) {
//            Preferences.getInstance().setAppState(state);
//        }

        State = state;
        return true;
    }
}
