package com.bigct.appint.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.bigct.appint.Constants;
import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.ui.adapter.DrawerAdapter;
import com.bigct.appint.ui.fragment.DashboardFragment;
import com.bigct.appint.ui.fragment.MyAccountFragment;
import com.bigct.appint.ui.fragment.ReqLogFragment;
import com.bigct.appint.ui.fragment.WebViewFragment;
import com.bigct.appint.ui.fragment.WifiDetailFragment;
import com.bigct.appint.model.MenuItem;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.listener.IOnItemClickListener;
import com.bigct.appint.view.DrawerDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IOnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter mDrawerAdapter;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private List<MenuItem> mMenuItemList;
    Fragment mCurFragment;
    String mCurFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        setupRecyclerView();
        setupDrawer(savedInstanceState);

        mTitle = getTitle();

//        WebService.sendAppInfo( MobixApplication.getInstance().getUser(),
//                                MobixApplication.getInstance().getAppInfo(),
//                                MobixApplication.getInstance().getCrtNetInfo(),
//                                this, null);
    }

    @Override
    protected void onPause() {
        MobixApplication.getInstance().awayCurrentActivity(this);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        MobixApplication.getInstance().awayCurrentActivity(this);
        Log.d("MainActicity", "Destroy");

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobixApplication.getInstance().setCurrentActivity(this);
    }

    public void setupActionBar() {
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar bar = getSupportActionBar();
//        bar.setDisplayUseLogoEnabled(false);
//        bar.setDisplayShowTitleEnabled(true);
//        bar.setDisplayShowHomeEnabled(true);
//        bar.setDisplayHomeAsUpEnabled(false);
//        bar.setHomeButtonEnabled(true);
    }

    public void setupRecyclerView() {
        // reference
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_drawer_recycler);

        // set layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // load categories from database
        loadMenuItemList();

        // set adapter
        if(recyclerView.getAdapter()==null)
        {
            // create adapter
            mDrawerAdapter = new DrawerAdapter(mMenuItemList, this);
        }
        else
        {
            // refill adapter
            mDrawerAdapter.refill(mMenuItemList, this);
        }
        recyclerView.setAdapter(mDrawerAdapter);

        // add decoration
        List<Integer> dividerPositions = new ArrayList<>();
        dividerPositions.add(2);
        dividerPositions.add(3);
        dividerPositions.add(4);
        dividerPositions.add(5);
        RecyclerView.ItemDecoration itemDecoration = new DrawerDividerItemDecoration(
                this,
                null,
                dividerPositions,
                getResources().getDimensionPixelSize(R.dimen.global_spacing_xxs));
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void setupDrawer(Bundle savedInstanceState) {
        mTitle = getTitle();
        mDrawerTitle = getTitle();

        // reference
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // set drawer
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //mDrawerLayout.setStatusBarBackgroundColor(ResourcesHelper.getValueOfAttribute(this, R.attr.colorPrimaryDark));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View view)
            {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(mTitle);
                }
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(mTitle);
                }
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // show initial fragment
        if(savedInstanceState == null)
        {
            selectDrawerItem(0);
        }
    }

    private void loadMenuItemList()
    {
        mMenuItemList = new ArrayList<MenuItem>();
        mMenuItemList.add(new MenuItem("dashboard", "Dashboard", "assets://icon/menu_dashboard.png", "6"));
        mMenuItemList.add(new MenuItem("myaccount", "My account", "assets://icon/menu_myaccount.png", "2"));
        mMenuItemList.add(new MenuItem("membership", "Membership", "assets://icon/menu_aboutus.png", ""));
        mMenuItemList.add(new MenuItem("notifications", "Notifications", "assets://icon/menu_myaccount.png", "2"));
        mMenuItemList.add(new MenuItem("settings", "Settings", "assets://icon/menu_settings.png", "3"));
        mMenuItemList.add(new MenuItem("logs", "Log", "assets://icon/menu_dashboard.png", "6"));
        mMenuItemList.add(new MenuItem("support", "Support", "assets://icon/menu_aboutus.png", ""));
        mMenuItemList.add(new MenuItem("logout", "Logout", "assets://icon/menu_logout.png", ""));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
        mDrawerToggle.onConfigurationChanged(newConfiguration);
    }

    public void setTitle(CharSequence title)
    {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onItemClick(View view, int position, long id, int viewType)
    {
        // position
        int menuItemPosition = mDrawerAdapter.getMenuItemPosition(position);
        selectDrawerItem(menuItemPosition);

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();
    }


    public void toggleMenu()
    {
        if (mDrawerLayout != null)
        {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                mDrawerLayout.closeDrawers();
            else
                mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }


    public void setWifiDetailFragment(NetInfo netInfo)
    {
        mCurFragmentId = "wifidetail";
        mCurFragment = WifiDetailFragment.newInstance(mCurFragmentId, netInfo);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_container, mCurFragment).commitAllowingStateLoss();
    }


    public void setFragment(String id)
    {
        mCurFragmentId = id;

        if (mCurFragmentId.equals("dashboard")) {
            mCurFragment = DashboardFragment.newInstance(mCurFragmentId);
        }
        else if (mCurFragmentId.equals("logs")) {
            mCurFragment = ReqLogFragment.newInstance(mCurFragmentId);
        }
        else if (mCurFragmentId.equals("logout")){
            MobixApplication.getInstance().doLogout();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
            return;
        }
        else if (mCurFragmentId.equals("myaccount")) {
            mCurFragment = MyAccountFragment.newInstance(mCurFragmentId);
        }
        else if (mCurFragmentId.equals("membership")) {
            String url = Constants.getMembershipUrl();
            mCurFragment = WebViewFragment.newInstance(mCurFragmentId, "Membership", url);
        }
        else if (mCurFragmentId.equals("notifications")) {
            String url = Constants.getNotificastionsUrl();
            mCurFragment = WebViewFragment.newInstance(mCurFragmentId, "Notifications", url);
        }
        else if (mCurFragmentId.equals("settings")) {
            String url = Constants.getSettingsUrl();
            mCurFragment = WebViewFragment.newInstance(mCurFragmentId, "Settings", url);
        }
        else if (mCurFragmentId.equals("support")) {
            String url = Constants.getSupportUrl();
            mCurFragment = WebViewFragment.newInstance(mCurFragmentId, "Support", url);
        }
        else
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_container, mCurFragment).commitAllowingStateLoss();
    }

    private void selectDrawerItem(int position) {
        setFragment(mMenuItemList.get(position).getId());

//        mDrawerAdapter.setSelected(mDrawerAdapter.getRecyclerPositionByCategory(position));
//        setTitle(mCategoryList.get(position).getName());
//        mDrawerLayout.closeDrawer(mDrawerScrimInsetsFrameLayout);
    }

    public void fireNetInfoListChanged(List<NetInfo> netInfoList) {
        if (mCurFragmentId == null || mCurFragment == null)
            return;
        if (mCurFragmentId.equals("dashboard"))
            ((DashboardFragment)mCurFragment).setNetInfoList(netInfoList);
    }
}
