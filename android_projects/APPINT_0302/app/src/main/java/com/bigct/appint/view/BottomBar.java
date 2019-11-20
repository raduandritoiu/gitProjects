package com.bigct.appint.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.ui.activity.LoginActivity;
import com.bigct.appint.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdragon on 2/20/2016.
 */
public class BottomBar extends FrameLayout {
    ImageView mIvTabBg;
    List<ImageView> mIvTabs = new ArrayList<>();

    MainActivity mActivity;

    int mSize = 0;
    int mSelectedTabId=0;

    public BottomBar(Context context) {
        super(context);
    }
    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        init();
    }

    public void init() {
        mIvTabBg = (ImageView)findViewById(R.id.iv_tab_bg);

        mIvTabs.add((ImageView) findViewById(R.id.iv_tab_1));
        mIvTabs.add((ImageView) findViewById(R.id.iv_tab_2));
        mIvTabs.add((ImageView)findViewById(R.id.iv_tab_3));
        mIvTabs.add((ImageView)findViewById(R.id.iv_tab_4));
        mIvTabs.add((ImageView)findViewById(R.id.iv_tab_5));

        for (int i = 0; i < mIvTabs.size(); i++) {
            mIvTabs.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getId() == R.id.iv_tab_1) {
                        // dashboard

                    }else if (v.getId() == R.id.iv_tab_2) {
                        // myaccount
                        if (mActivity != null)
                            mActivity.setFragment("myaccount");
                    }else if (v.getId() == R.id.iv_tab_3) {
                        // menu
                        if (mActivity != null)
                            mActivity.toggleMenu();
                    }else if (v.getId() == R.id.iv_tab_4) {
                        // settings
                        if (mActivity != null)
                            mActivity.setFragment("settings");
                    }else if (v.getId() == R.id.iv_tab_5) {
                        // log out
                        MobixApplication.getInstance().doLogout();
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                        MobixApplication.getInstance().getCurrentActivity().finish();
                    }
                }
            });
        }

        int w1 = getDisplay().getWidth() / 5;

        int w=((LinearLayout)mIvTabs.get(0).getParent()).getMeasuredWidth();
        int h=((LinearLayout)mIvTabs.get(0).getParent()).getMeasuredHeight();
        mSize = Math.max(Math.max(w, h),w1);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(mSize, ViewGroup.LayoutParams.WRAP_CONTENT);
        mIvTabBg.setLayoutParams(lp);

        mIvTabBg.setX(mSize*mSelectedTabId);
    }

    public void setSelect(int i) {
        if (mSelectedTabId == i)
            return;

        mSelectedTabId = i;

        if (mIvTabBg == null)
            return;

//        float x1 = mIvTabBg.getX();
//        float y1 = mIvTabBg.getY();
//        float w1 = mIvTabBg.getWidth();
//        float h1 = mIvTabBg.getHeight();

        mIvTabBg.setX(mSize * i - mSize / 2);
        //mIvTabBg.setY(y+h/2-h1/2);
    }

    public void setActivity(MainActivity activity)
    {
        mActivity = activity;
    }
}
