package com.bigct.appint.model;

/**
 * Created by sdragon on 1/21/2016.
 */
public class NetInfo {
    public final static int COMMAND_NONE = 0;
    public final static int COMMAND_DBM_RANGE = 2;

    public final static int SIGNAL_NONE = -1000;

    String uid = "";
    String name = "";
    String ssid = "";
    String pwd = "";
    String ip = "";
    String openCommand;
    int dbmMax; // max signal strength
    int dbmMin; // min signal strength
    int dbmExtended;
    int internet;
    int automatic;

    // scan result params
    String caps = ""; // capabilities.
    int signal = SIGNAL_NONE; // current signal strength in dbm

    boolean isconnected;
    boolean isconnecting;
    int doorsOpened = 0;
    long connectingStartTime;
    long unavailableStartTime;
    long unavailableTimeout = 120000; // 2 minutes in miliseconds


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOpenCommand() {
        return openCommand;
    }

    public void setOpenCommand(String command) {
        this.openCommand = command;
    }

    public int getAutomatic() {
        return automatic;
    }

    public void setAutomatic(int automatic) {
        this.automatic = automatic;
    }

    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    public int getDbmMax() {
        return dbmMax;
    }

    public void setDbmMax(int dbmMax) {
        this.dbmMax = dbmMax;
    }

    public int getDbmMin() {
        return dbmMin;
    }

    public void setDbmMin(int dbmMin) {
        this.dbmMin = dbmMin;
    }

    public int getDbmExtended() {
        return dbmExtended;
    }

    public void setDbmExtended(int dbmExtended) {
        this.dbmExtended = dbmExtended;
    }

    public String getCaps() {
        return caps;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        if (signal == 0)
            this.signal = SIGNAL_NONE;
        else
            this.signal = signal;
    }

    public boolean isInDbmRange() {
        return signal == SIGNAL_NONE ? false : signal >= dbmMin && signal <= dbmMax;
    }

    public boolean isInDbmExtended() {
        return signal == SIGNAL_NONE ? false : signal >= dbmExtended && signal <= dbmMax;
    }

    public boolean isJustInDbmExtended() {
        return signal == SIGNAL_NONE ? false : signal >= dbmExtended && signal <= dbmMin;
    }

    public boolean isconnecting() {
        return isconnecting;
    }

    public boolean isconnected() {
        return isconnected;
    }

    public long getConnectingStartTime() {
        return connectingStartTime;
    }

    public void setIsconnecting(boolean isconnecting) {
        this.isconnecting = isconnecting;
    }

    public void setIsconnected(boolean isconnected) {
        this.isconnected = isconnected;
    }

    public void connecting(long crtTime) {
        connectingStartTime = crtTime;
        isconnecting = true;
        isconnected = false;
    }

    public void connected() {
        isconnecting = false;
        isconnected = true;
    }

    public void disconected() {
        isconnecting = false;
        isconnected = false;
        doorsOpened = 0;
    }

    public boolean openDoor() {
        if (doorsOpened > 4) {
            return false;
        }
        doorsOpened++;
        return true;
    }

    public void makeUnavailable(long crtTime) {
        unavailableStartTime = crtTime;
    }

    public boolean isAvailable(long crtTime) {
        long diff = crtTime - unavailableStartTime;
        return crtTime - unavailableStartTime > unavailableTimeout;
    }
}
