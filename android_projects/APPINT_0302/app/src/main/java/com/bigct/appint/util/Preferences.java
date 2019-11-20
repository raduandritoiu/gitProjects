package com.bigct.appint.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.model.User;

import java.util.Calendar;
import java.util.Date;

public class Preferences
{
	private SharedPreferences mSharedPreferences;
	private Context mContext;

	static Preferences preferences;
	public static void open(Context context) {
		if (preferences == null)
			preferences = new Preferences(context);
	}
	public static Preferences getInstance() {
		return preferences;
	}
	public Preferences(Context context)
	{
		if(context==null) context = MobixApplication.getContext();
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mContext = context;
	}

	public void clearPreferences()
	{
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	public boolean isAppMonitorServiceStarted() {
        boolean isAppMonitorServiceStarted = mSharedPreferences.getBoolean("isAppMonitorServiceStarted", false);
		return  false;//isAppMonitorServiceStarted;
	}

	public void setAppMonotorServiceStarted(boolean isStarted) {
        //write to preferences.
        Editor editor = mSharedPreferences.edit();
        editor.putBoolean("isAppMonitorServiceStarted", isStarted);

		editor.commit();
	}

	public User getUser() {
        User user = new User();
        user.setId(mSharedPreferences.getString("id", ""));
        user.setEmail(mSharedPreferences.getString("email", ""));
        user.setPwd(mSharedPreferences.getString("pwd", ""));
		return user;
	}

	public void setUser(User user) {
        //write to preferences.
        Editor editor = mSharedPreferences.edit();
        editor.putString("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("pwd", user.getPwd());
        editor.commit();
	}

	public void setAppState(int state) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt("appState", state);
		editor.commit();
	}

	public int getAppState() {
		return mSharedPreferences.getInt("appState", 0);
	}

	// set installation time to preference.
	public void setInstalledTime(Date date) {
		Editor editor = mSharedPreferences.edit();
		Date now = date; // new Date();
		String nowStr = now.getYear() + "/"+now.getMonth() + "/" + now.getDay();
//		editor.putString("installed_time", nowStr);
		editor.putLong("installed_time", now.getTime());
		editor.commit();
	}
	public Date getInstalledTime() {
		Calendar c = Calendar.getInstance();
//		String installedTime = mSharedPreferences.getString("installed_time", "");
		long installedTime = mSharedPreferences.getLong("installed_time", 0);

		if (installedTime == 0)
			return null;


		c.setTimeInMillis(installedTime);
		return c.getTime();
	}
}
