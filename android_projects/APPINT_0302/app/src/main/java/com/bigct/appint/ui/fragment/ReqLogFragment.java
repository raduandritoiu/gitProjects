package com.bigct.appint.ui.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bigct.appint.R;
import com.bigct.appint.database.dao.ReqLogDao;
import com.bigct.appint.database.model.ReqLogModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sdragon on 2/11/2016.
 */
public class ReqLogFragment extends TaskFragment {
    private View mRootView;
    private ListView mLogList;
    private ReqLogAdapter mAdapter;
    private List<ReqLogModel> mLogs;

    public static ReqLogFragment newInstance(String id)
    {
        ReqLogFragment fragment = new ReqLogFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_log, container, false);
        setupListView();

        //setNetInfoList(MobixApplication.getInstance().getNetInfoList());
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
        setLogList();
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

    }

    public void setupListView()
    {
        mLogList = (ListView)mRootView.findViewById(R.id.lv_loglist);
        try
        {
            mLogs = ReqLogDao.readAll(-1, -1);
        }catch (SQLException e)
        {

        }
        mAdapter = new ReqLogAdapter(getContext(), mLogs);

        mLogList.setAdapter(mAdapter);
    }

    public void setLogList()
    {

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



    public class ReqLogAdapter extends ArrayAdapter<ReqLogModel> {
        public ReqLogAdapter(Context context, List<ReqLogModel> reqLogs) {
            super(context, 0, reqLogs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ReqLogModel reqLog = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(android.R.id.text1);
            // Populate the data into the template view using the data object
            tvName.setText(reqLog.getReqtime()+" "+reqLog.getRequrl());

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
