package com.bigct.appint.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.ui.activity.MainActivity;
import com.bigct.appint.model.User;

/**
 * Created by sdragon on 2/11/2016.
 */
public class MyAccountFragment extends TaskFragment {
    View mRootView;

    TextView mTvFirstName;
    TextView mTvLastName;
    TextView mTvEmail;
    TextView mTvPhonenumber;
    TextView mTvBirthday;
    ImageView mIvPhoto;

    public static MyAccountFragment newInstance(String id)
    {
        MyAccountFragment fragment = new MyAccountFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_myaccount, container, false);

        mTvFirstName = (TextView)mRootView.findViewById(R.id.tv_firstname);
        mTvLastName = (TextView)mRootView.findViewById(R.id.tv_lastname);
        mTvEmail = (TextView)mRootView.findViewById(R.id.tv_email);
        mTvPhonenumber = (TextView)mRootView.findViewById(R.id.tv_phonenumber);
        mTvBirthday = (TextView)mRootView.findViewById(R.id.tv_birthday);
        mIvPhoto = (ImageView)mRootView.findViewById(R.id.iv_photo);

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
        User user = MobixApplication.getInstance().getUser();
        if (user == null)
            return;;

        if (mTvFirstName != null)
            mTvFirstName.setText(user.getFirstname());
        if (mTvLastName != null)
            mTvLastName.setText(user.getLastname());
        if (mTvEmail != null)
            mTvEmail.setText(user.getEmail());
        if (mTvPhonenumber != null)
            mTvPhonenumber.setText(user.getPhone());
        if (mTvBirthday != null)
            mTvBirthday.setText("1988/2/7");
    }

    public void setData(User user)
    {
        // set net info and update view.
        if (mTvFirstName != null)
            mTvFirstName.setText(user.getFirstname());
        if (mTvLastName != null)
            mTvLastName.setText(user.getLastname());
        if (mTvEmail != null)
            mTvEmail.setText(user.getEmail());
        if (mTvPhonenumber != null)
            mTvPhonenumber.setText(user.getPhone());
        if (mTvBirthday != null)
            mTvBirthday.setText("");
//        if (mIvPhoto != null)
//            mIvPhoto.setImageURI("");
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
