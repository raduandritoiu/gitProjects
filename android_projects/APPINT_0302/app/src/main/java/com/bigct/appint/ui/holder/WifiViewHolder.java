package com.bigct.appint.ui.holder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.network.NetworkUtility;
import com.bigct.appint.network.webservice.WebService;
import com.bigct.appint.listener.IOnItemClickListener;
import com.bigct.appint.util.SoundPlayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by radua on 23.03.2016.
 */
public final class WifiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    IOnItemClickListener mListener;
    TextView mTvSsid;
    TextView mTvSignal;
    Button mBtnOpen;
    NetInfo mNetInfo;

    public WifiViewHolder(final View itemView, IOnItemClickListener listener, ImageLoader imageLoader, DisplayImageOptions displayImageOptions, ImageLoadingListener imageLoadingListener)
    {
        super(itemView);
        mListener = listener;

        // set listener
        itemView.setOnClickListener(this);

        mTvSsid = (TextView)itemView.findViewById(R.id.tv_ssid);
        mTvSignal = (TextView)itemView.findViewById(R.id.tv_signal);
        mBtnOpen = (Button)itemView.findViewById(R.id.btn_open);
        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNetInfo == null)
                    return;

                if (mNetInfo.isconnected()) {
                    Toast.makeText(itemView.getContext(), "Aleady connected", Toast.LENGTH_SHORT).show();
                    WebService.openDoor(MobixApplication.getInstance().getUser(), mNetInfo, MobixApplication.getInstance(), null);
                    WebService.sendCommand(mNetInfo, MobixApplication.getInstance(), null);
                    SoundPlayer.playBeep();
                }
                else if (mNetInfo.isInDbmExtended()) {
                    NetInfo netInfo = MobixApplication.getInstance().getCrtNetInfo();
                    if (netInfo != null) {
                        if (mNetInfo.getSsid().equals(netInfo.getSsid())) {
                            Toast.makeText(itemView.getContext(), "Already Connecting " + mNetInfo.isconnecting(), Toast.LENGTH_SHORT).show();
                            // do nothing
                            return;
                        }
                        else {
                            netInfo.disconected();
                        }
                    }
                    Toast.makeText(itemView.getContext(), "Connecting " + mNetInfo.getName(), Toast.LENGTH_SHORT).show();
                    NetworkUtility.connectToWifi(mNetInfo, MobixApplication.getInstance());
                    mNetInfo.connecting(System.currentTimeMillis());
                    MobixApplication.getInstance().setCrtNetInfo(mNetInfo);
                }
                else if (mNetInfo.getInternet() == 1) {
                    WebService.sendCommand(mNetInfo, MobixApplication.getInstance(), null);
                }
                else {
                    Toast.makeText(itemView.getContext(), "Not in Range " + mNetInfo.getName()+mNetInfo.getSignal(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        mListener.onItemClick(view, getPosition(), getItemId(), getItemViewType());
    }

    public void setNetInfo(NetInfo netInfo)
    {
        mNetInfo = netInfo;
        if (netInfo == null)
            return;

        Activity curActivity = MobixApplication.getInstance().getCurrentActivity();
        if (curActivity == null)
            return;

        Log.d("--BindData--", mNetInfo.getSsid() + mNetInfo.getSignal());
        curActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvSsid != null)
                    mTvSsid.setText(mNetInfo.getName());//mTvSsid.setText(netInfo.getSsid());
                if (mTvSignal != null) {
                    if (mNetInfo.getSignal() <= NetInfo.SIGNAL_NONE)
                        mTvSignal.setText(" : "+ mNetInfo.getDbmExtended() + " " + mNetInfo.getDbmMin() + " ~ " + mNetInfo.getDbmMax());
                    else
                        mTvSignal.setText(String.valueOf(mNetInfo.getSignal())+ " : "+ mNetInfo.getDbmExtended() + " " + mNetInfo.getDbmMin() + " ~ " + mNetInfo.getDbmMax());
                }
                if (mBtnOpen != null) {
                    if (mNetInfo.isInDbmRange()) {
                        Log.d("DashboardAdapter", "green");
                        mBtnOpen.setBackgroundResource(R.drawable.selector_open_green);
                    }
                    else if (mNetInfo.isInDbmExtended()) {
                        mBtnOpen.setBackgroundResource(R.drawable.selector_open_yellow);
                    }
                    else if (mNetInfo.getSignal() == NetInfo.SIGNAL_NONE && mNetInfo.getInternet() == 1) {
                        Log.d("DashboardAdapter", "blue");
                        mBtnOpen.setBackgroundResource(R.drawable.selector_open_blue);
                    }
                    else {
                        Log.d("DashboardAdapter", "gray");
                        mBtnOpen.setBackgroundResource(R.drawable.selector_open_gray);
                    }
                }
            }
        });
    }
}


