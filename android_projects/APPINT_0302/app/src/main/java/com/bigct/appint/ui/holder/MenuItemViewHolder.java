package com.bigct.appint.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigct.appint.MobixApplication;
import com.bigct.appint.R;
import com.bigct.appint.model.MenuItem;
import com.bigct.appint.listener.IOnItemClickListener;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by radua on 23.03.2016.
 */
public final class MenuItemViewHolder extends SwappingHolder implements View.OnClickListener
{
    private TextView nameTextView;
    private TextView countTextView;
    private ImageView iconImageView;
    private IOnItemClickListener mListener;
    private SingleSelector mSingleSelector;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoadingListener mImageLoadingListener;


    public MenuItemViewHolder(View itemView, IOnItemClickListener listener, SingleSelector singleSelector, ImageLoader imageLoader, DisplayImageOptions displayImageOptions, ImageLoadingListener imageLoadingListener)
    {
        super(itemView, singleSelector);
        mListener = listener;
        mSingleSelector = singleSelector;
        mImageLoader = imageLoader;
        mDisplayImageOptions = displayImageOptions;
        mImageLoadingListener = imageLoadingListener;

        // set selection background
        setSelectionModeBackgroundDrawable(MobixApplication.getContext().getResources().getDrawable(R.drawable.selector_selectable_item_bg));
        setSelectionModeStateListAnimator(null);

        // set listener
        itemView.setOnClickListener(this);

        // find views
        nameTextView = (TextView) itemView.findViewById(R.id.drawer_item_name);
        countTextView = (TextView) itemView.findViewById(R.id.drawer_item_count);
        iconImageView = (ImageView) itemView.findViewById(R.id.drawer_item_icon);
    }


    @Override
    public void onClick(View view)
    {
        mListener.onItemClick(view, getPosition(), getItemId(), getItemViewType());
    }


    public void bindData(MenuItem menuItem)
    {
        nameTextView.setText(menuItem.getName());
        countTextView.setVisibility(View.GONE); // not implemented
        mImageLoader.displayImage(menuItem.getImage(), iconImageView, mDisplayImageOptions, mImageLoadingListener);
    }
}