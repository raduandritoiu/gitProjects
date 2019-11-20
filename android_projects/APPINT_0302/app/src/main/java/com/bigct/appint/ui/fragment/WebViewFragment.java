package com.bigct.appint.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bigct.appint.R;
import com.bigct.appint.ui.activity.MainActivity;

/**
 * Created by sdragon on 2/11/2016.
 */
public class WebViewFragment extends TaskFragment {
    View mRootView;
    TextView mTvTitle;
    WebView mWvContent;

    String mTitle;
    String mUrl;

    public static WebViewFragment newInstance(String id, String title, String url)
    {
        WebViewFragment fragment = new WebViewFragment();
        fragment.setData(title, url);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_webview, container, false);

        mTvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
        mWvContent = (WebView)mRootView.findViewById(R.id.wv_content);


        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mRootView.findViewById(R.id.iv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setFragment("dashboard");
            }
        });
        mRootView.findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleMenu();
            }
        });

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        renderView();
        // show toolbar if hidden
        showToolbar(true);
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }


    @Override
    public void onResume()
    {
        super.onResume();
    }


    @Override
    public void onPause()
    {
        super.onPause();

    }


    @Override
    public void onStop()
    {
        super.onStop();
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mRootView = null;
    }


    @Override
    public void onDestroy() { super.onDestroy(); }


    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    public void renderView()
    {
        // set net info and update view.
        if (mTvTitle != null)
            mTvTitle.setText(mTitle);
        if (mWvContent != null) {
            mWvContent.loadUrl(mUrl);//"http://www.pointit.ro/mobile-api/ssid/<user_uid>/<ssid_uid>/<phone_number>");
        }
    }

    public void setData(String title, String url)
    {
        mTitle = title;
        mUrl = url;
    }

    private void showToolbar(boolean visible)
    {
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar == null)
            return;
        if(visible)
        {
            toolbar.animate()
                    .translationY(0)
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener()
                    {
                        @Override
                        public void onAnimationStart(Animator animator)
                        {
                            toolbar.setVisibility(View.VISIBLE);
                            toolbar.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator)
                        {
                            toolbar.setEnabled(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
        }
        else
        {
            toolbar.animate()
                    .translationY(-toolbar.getBottom())
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener()
                    {
                        @Override
                        public void onAnimationStart(Animator animator)
                        {
                            toolbar.setEnabled(false);
                        }


                        @Override
                        public void onAnimationEnd(Animator animator)
                        {
                            toolbar.setVisibility(View.GONE);
                            toolbar.setEnabled(true);
                        }


                        @Override
                        public void onAnimationCancel(Animator animator)
                        {
                        }


                        @Override
                        public void onAnimationRepeat(Animator animator)
                        {
                        }
                    });
        }
    }
}
