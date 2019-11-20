package com.bigct.appint;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bigct.appint.ui.activity.MainActivity;
import com.bigct.appint.ui.activity.WifiInfoDetailActivity;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.model.AppInfo;
import com.bigct.appint.model.LoginErr;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.model.User;
import com.bigct.appint.network.NetworkUtility;
import com.bigct.appint.network.webservice.JsonParser;
import com.bigct.appint.service.AppMonitorService;
import com.bigct.appint.util.Preferences;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MobixApplication extends Application
{
	private static MobixApplication mInstance;

	// sorted net info list from server
    List<NetInfo> mNetInfoList = new ArrayList<>();
	// current network connection info
	NetInfo mCrtNetInfo = null;
	// wifi scan result handler from wifi scan receiver
	Handler mWifiScanResultHandler;
	// wifi connection change hander from wifi change receiver
	Handler	mWifiChangeHandler;
	// the wifi was scaned at least once
	boolean mWifiWasScaned = false;
	// current logined user info
	User mUser = new User();
	// current activity
	Activity mCurrentActivity;


	public MobixApplication()
	{
		mInstance = this;
	}


	//------------------------------
	// getter and setters
	//------------------------------
	public void setNetInfoList(List<NetInfo> netInfoList) {
		mNetInfoList = netInfoList;
	}
    public List<NetInfo> getNetInfoList() {
		return mNetInfoList;
	}

    public void setUser(User user) {
		mUser = user;
		Preferences.getInstance().setUser(user);
	}
    public User getUser() {
		return mUser;
	}

	public void setCrtNetInfo(NetInfo netInfo) {
		mCrtNetInfo = netInfo;
	}
	public NetInfo getCrtNetInfo() {
		return mCrtNetInfo;
	}

	public AppInfo getAppInfo() {
		return AppInfo.getInstance();
	}

	public boolean isWifiScanned() {
		return mWifiWasScaned;
	}

	public static Context getContext() {
		return mInstance;
	}
	public static MobixApplication getInstance() {
		return mInstance;
	}

	public void setCurrentActivity(Activity activity) {
		mCurrentActivity = activity;
	}
	public Activity getCurrentActivity() {
		return mCurrentActivity;
	}
	public void awayCurrentActivity(Activity activity) {
		if (activity != null && activity.equals(mCurrentActivity))
			mCurrentActivity=null;
	}

	public Handler getWifiScanResultHandler() {
		return mWifiScanResultHandler;
	}

	public Handler getWifiChangeHandler() {
 		return mWifiChangeHandler;
 	}
	//-------------------------------

	@Override
	public void onCreate()
	{
		super.onCreate();

		// force AsyncTask to be initialized in the main thread due to the bug:
		// http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
		try {
			Class.forName("android.os.AsyncTask");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		// preferences instance
		Preferences.open(this);

		// service start (and service thread)
		if (!AppMonitorService.mIsRunning) {
			Context context = getApplicationContext();
			context.startService(new Intent(context, AppMonitorService.class));
		}

		// load user information
		mUser = Preferences.getInstance().getUser();

		// init image caching
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		cacheDir.mkdirs(); // requires android.permission.WRITE_EXTERNAL_STORAGE

		// loading image loader configuration.
		try {
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
					.threadPoolSize(3)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
					.diskCache(new LruDiscCache(cacheDir, new HashCodeFileNameGenerator(), 32 * 1024 * 1024))
					.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
					.build();

			ImageLoader.getInstance().init(config);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		// initialize log list in db.
		try {
			ReqLogDao.deleteAll();
		}
		catch (SQLException e) {}

		// handler for wifi scan result
        mWifiScanResultHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				onWifiScanResultHandler();
			}
		};
		mWifiChangeHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				onWifiChangeHandler(msg);
			}
		};
	}


	/* callback for wifi change */
	public void onWifiChangeHandler(Message msg) {
		Bundle bundle = msg.getData();

		String ssid = bundle.getString("ssid");
		int signal = bundle.getInt("signal");

		ssid = ssid.replace("\"", "");

		for (NetInfo netInfo : mNetInfoList) {
			if (netInfo.getSsid().equals(ssid)) {
				netInfo.setSignal(signal);
				setCrtNetInfo(netInfo);
			}
		}
	}


	/* Callback for Wifi Scan Result. */
	public void onWifiScanResultHandler()
	{
		// get info from WifiManager
		List<ScanResult> scanResults = null;
		String connectedSsid = "none";
		WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			// get scan results
			scanResults = wifiManager.getScanResults();
			// get connected ssid
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			if (wifiInfo != null) {
				connectedSsid = wifiInfo.getSSID().replace("\"", "");
			}
		}

		List<NetInfo> netInfoList = getNetInfoList();
		mWifiWasScaned = true;
		if (scanResults == null || netInfoList == null)
			return;

		// matching scan result and netinfo
		// find and match wifi network status with server network info list.
		// check best signal ssid with connected wifi.
		NetInfo bestNetInfo = null;
		NetInfo connNetInfo = null;
		for (NetInfo netInfo : netInfoList) {
			netInfo.setSignal(0);
			netInfo.setCaps("");
			for (ScanResult sr : scanResults) {
				sr.SSID = sr.SSID.replace("\"", "");
				if (netInfo.getSsid().equals(sr.SSID)) {
					netInfo.setSignal(sr.level);
					netInfo.setCaps(sr.capabilities);
					break;
				}
			}
			if (netInfo.isInDbmExtended() && netInfo.getAutomatic() == 1 && netInfo.isAvailable(System.currentTimeMillis())) {
				if (bestNetInfo == null || bestNetInfo.getSignal() > netInfo.getSignal()) {
					bestNetInfo = netInfo;
				}
			}
			if (connectedSsid.equals(netInfo.getSsid())) {
				connNetInfo = netInfo;
			}
		}

		// reset the crtNetInfo if it is not in the process of connecting and is not the connected one
		if (mCrtNetInfo != null && !mCrtNetInfo.isconnecting() && !mCrtNetInfo.getSsid().equals(connectedSsid)) {
			mCrtNetInfo.disconected();
			mCrtNetInfo = null;
		}

		// set the crtNetInfo
		if (mCrtNetInfo == null) {
			if (connNetInfo != null) {
				// set current connected network
				connNetInfo.connected();
				setCrtNetInfo(connNetInfo);
			}
			else if (bestNetInfo != null) {
				// set best strength network - and connect to it
				NetworkUtility.connectToWifi(bestNetInfo, this);
				bestNetInfo.connecting(System.currentTimeMillis());
				setCrtNetInfo(bestNetInfo);
			}
		}

		// if activity is showed, fire to activity.
        if (mCurrentActivity != null) {
			// sort net info list by signal length in descending order. - this is for the UI
			Collections.sort(netInfoList, new Comparator<NetInfo>(){
				public int compare(NetInfo a, NetInfo b) {
					if (b.getSignal() < a.getSignal()) return -1;
					else if (b.getSignal() > a.getSignal()) return 1;
					else return 0;
				}
			});
            if (mCurrentActivity.getClass() == MainActivity.class) {
                ((MainActivity) mCurrentActivity).fireNetInfoListChanged(netInfoList);
            }
            else if (mCurrentActivity.getClass() == WifiInfoDetailActivity.class) {
                ((WifiInfoDetailActivity) mCurrentActivity).fireNetInfoListChanged(netInfoList);
            }
		}
	}


	public void doLogout() {
		mNetInfoList.clear();
		mCrtNetInfo = null;
		AppState.setState(AppState.STATE_LOGOUT);
		setUser(new User());
	}


	public boolean handleLogin(String response) {
		List<NetInfo> netInfoList = new ArrayList<>();
		LoginErr loginErr = new LoginErr();
		String uid = JsonParser.parseLoginReponse(response, netInfoList, loginErr);
		if (!uid.isEmpty()) {
			User user = new User();
			user.setId(uid);
			user.setEmail(mUser.getEmail());
			user.setPwd(mUser.getPwd());

			setNetInfoList(netInfoList);
			setUser(user);

			AppState.setState(AppState.STATE_RUNNING);
			return true;
		}
		else {
			AppState.setState(AppState.STATE_READY);
			Toast.makeText(this, loginErr.getError(), Toast.LENGTH_SHORT).show();
		}
		return false;
	}


	public void updatesetNetInfoList(List<NetInfo> netInfos) {
		setNetInfoList(netInfos);
		if (mCurrentActivity != null) {
			if (mCurrentActivity.getClass() == MainActivity.class) {
				((MainActivity) mCurrentActivity).fireNetInfoListChanged(netInfos);
			}
			else if (mCurrentActivity.getClass() == WifiInfoDetailActivity.class) {
				((WifiInfoDetailActivity) mCurrentActivity).fireNetInfoListChanged(netInfos);
			}
		}

	}

	public NetInfo getConnectedNetInfo()
	{
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			WifiInfo connectionInfo = wifiManager.getConnectionInfo();
			String ssid = connectionInfo.getSSID().replace("\"", "");
			for (NetInfo ni : getNetInfoList()) {
				if (ni.getSsid().equals(ssid)) {
					ni.setSignal(connectionInfo.getRssi());
					return ni;
				}
			}
		}
		return null;
	}

	public String getConnectedNetSsid()
	{
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			WifiInfo connectionInfo = wifiManager.getConnectionInfo();
			String ssid = connectionInfo.getSSID().replace("\"", "");
			return ssid;
		}
		return null;
	}
}
