package com.bigct.appint.ui.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.listener.IHttpResponseListener;
import com.bigct.appint.model.LoginErr;
import com.bigct.appint.network.webservice.JsonParser;
import com.bigct.appint.network.webservice.WebService;
import com.bigct.appint.ui.adapter.WifiAdapter;
import com.bigct.appint.ui.activity.MainActivity;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.listener.IOnItemClickListener;
import com.bigct.appint.view.BottomBar;
import com.bigct.appint.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdragon on 1/20/2016.
 */
public class DashboardFragment extends TaskFragment implements IOnItemClickListener
{
    private static final int LAZY_LOADING_TAKE = 128;
    private static final int LAZY_LOADING_OFFSET = 4;

    private boolean mLazyLoading = false;

    private View mRootView;
    private Handler mTimerHandler;
    private Runnable mTimerRunnable;
    private ImageView mRefresh;
    private WifiAdapter mAdapter;
    private BottomBar mBottomBar;
    private List<NetInfo> mNetInfoList = new ArrayList<>();


    public static DashboardFragment newInstance(String id)
    {
        return new DashboardFragment();
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // set search listener
        try
        {}
        catch(ClassCastException e)
        {}
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setupRecyclerView();
        mBottomBar = (BottomBar)mRootView.findViewById(R.id.bottomBar);
        mBottomBar.setActivity((MainActivity) getActivity());
        mBottomBar.setSelect(2);
        mRefresh = (ImageView) mRootView.findViewById(R.id.refresh_img);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebService.login(MobixApplication.getInstance().getUser(), MobixApplication.getInstance(), false, new IHttpResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        List<NetInfo> netInfoList = new ArrayList<>();
                        LoginErr loginErr = new LoginErr();
                        String uid = JsonParser.parseLoginReponse(response, netInfoList, loginErr);
                        MobixApplication.getInstance().updatesetNetInfoList(netInfoList);
                    }

                    @Override
                    public void onError(String response) {
                    }
                });
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        renderView();
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
        setNetInfoList(MobixApplication.getInstance().getNetInfoList());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mAdapter!=null)
            mAdapter.stop();
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
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        // save current instance state
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onItemClick(View view, int position, long id, int viewType)
    {
        // position
        int pos = mAdapter.getItemPosition(position);
        // start activity
        NetInfo netInfo = mNetInfoList.get(pos);
        ((MainActivity)getActivity()).setWifiDetailFragment(netInfo);
    }


    private void setupRecyclerView()
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getGridSpanCount());
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        RecyclerView recyclerView = getRecyclerView();
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    public void setNetInfoList(List<NetInfo> netInfoList)
    {
        mNetInfoList = netInfoList;
        renderView();
    }


    public void renderView()
    {
        final RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null)
            return;

        // content
        if(recyclerView.getAdapter() == null) {
            // create adapter
            mAdapter = new WifiAdapter(mNetInfoList, null, this, getGridSpanCount());
            // set fixed size
            recyclerView.setHasFixedSize(false);
            // add decoration
            RecyclerView.ItemDecoration itemDecoration = new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.fragment_dashboard_recycler_item_padding));
            recyclerView.addItemDecoration(itemDecoration);
            // set animator
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            // set adapter
            recyclerView.setAdapter(mAdapter);
        }
        else {
            // refill adapter
            mAdapter.refill(mNetInfoList, null, this, getGridSpanCount());
        }
    }

    private void showToolbar(boolean visible)
    {
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar == null)
            return;

        if (visible) {
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
        else {
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
                        {}

                        @Override
                        public void onAnimationRepeat(Animator animator)
                        {}
                    });
        }
    }


    private RecyclerView getRecyclerView()
    {
        return mRootView!=null ? (RecyclerView) mRootView.findViewById(R.id.dashboard_recycler) : null;
    }


    private int getGridSpanCount()
    {
        return 1;
    }
}
