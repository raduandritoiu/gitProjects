package com.bigct.appint.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bigct.appint.R;
import com.bigct.appint.listener.AnimateImageLoadingListener;
import com.bigct.appint.model.NetInfo;
import com.bigct.appint.ui.holder.WifiViewHolder;
import com.bigct.appint.listener.IOnItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by sdragon on 1/20/2016.
 */
public class WifiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageLoader mImageLoader = ImageLoader.getInstance();
    ImageLoadingListener mImageLoadingListener;
    IOnItemClickListener mListener;
    DisplayImageOptions mDisplayImageOptions;

    List<NetInfo> mNetInfoList;
    List<Object> mFooterList;
    int mGridSpanCount;
    boolean mAnimationEnabled;
    int mAnimationPosition = -1;


    public WifiAdapter(List<NetInfo> netInfoList, List<Object> footerList, IOnItemClickListener listener, int gridSpanCount)
    {
        mNetInfoList = netInfoList;
        mFooterList = footerList;
        mListener = listener;
        mGridSpanCount = gridSpanCount;

        // image caching options
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.transparent)
                .showImageForEmptyUri(R.drawable.placeholder_photo)
                .showImageOnFail(R.drawable.placeholder_photo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        mImageLoadingListener = new AnimateImageLoadingListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_dashboard_item, parent, false);
        WifiViewHolder viewHolder = new WifiViewHolder(view, mListener, mImageLoader, mDisplayImageOptions, mImageLoadingListener);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        if (mNetInfoList == null)
            return;
        NetInfo netInfo = mNetInfoList.get(getItemPosition(position));
        if (netInfo != null)
        {
            ((WifiViewHolder)viewHolder).setNetInfo(netInfo);
        }
        // set item margins
        setItemMargins(viewHolder.itemView, position);
        // set animation
        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount()
    {
        return (mNetInfoList == null ? 0:mNetInfoList.size());
    }

    @Override
    public int getItemViewType(int position)
    {
        return -1;
    }

    public void stop()
    {}

    public void setAnimationEnabled(boolean animationEnabled)
    {
        mAnimationEnabled = animationEnabled;
    }

    public int getItemPosition(int recyclerpos)
    {
        return recyclerpos;
    }

    public void refill(List<NetInfo> wifiList, List<Object> footerList, IOnItemClickListener listener, int gridSpanCount)
    {
        mNetInfoList  = wifiList;
        mFooterList = footerList;
        mListener = listener;
        mGridSpanCount = gridSpanCount;
        notifyDataSetChanged();
    }

    private void setAnimation(final View view, int position)
    {
        if(mAnimationEnabled && position>mAnimationPosition)
        {
            view.setScaleX(0f);
            view.setScaleY(0f);
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator());

            mAnimationPosition = position;
        }
    }

    private void setItemMargins(View view, int position)
    {
        return;

//        int height = (int) MobixApplication.getContext().getResources().getDimension(R.dimen.fragment_dashboard_recycler_item_size);
//        int marginTop = 0;
//
//        if(position<mGridSpanCount)
//        {
//            TypedArray a = MobixApplication.getContext().obtainStyledAttributes(null, new int[]{android.R.attr.actionBarSize}, 0, 0);
//            marginTop = (int) a.getDimension(0, 0);
//            a.recycle();
//
//            height += marginTop;
//        }
//
//        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//        marginParams.setMargins(0, marginTop, 0, 0);
//
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = height;
    }
}
