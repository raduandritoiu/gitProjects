package com.bigct.appint.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigct.appint.R;
import com.bigct.appint.listener.AnimateImageLoadingListener;
import com.bigct.appint.model.MenuItem;
import com.bigct.appint.ui.holder.MenuItemViewHolder;
import com.bigct.appint.listener.IOnItemClickListener;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_MENUITEM = 1;

	private List<MenuItem> mMenuItemList;
	private IOnItemClickListener mListener;
	private SingleSelector mSingleSelector = new SingleSelector();
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener mImageLoadingListener;


	public DrawerAdapter(List<MenuItem> menuItemList, IOnItemClickListener listener)
	{
		mMenuItemList = menuItemList;
		mListener = listener;
		mSingleSelector.setSelectable(true);

		// image caching options
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(android.R.color.transparent)
				.showImageForEmptyUri(android.R.color.transparent)
				.showImageOnFail(android.R.color.transparent)
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

		// inflate view and create view holder
		if(viewType==VIEW_TYPE_HEADER)
		{
			View view = inflater.inflate(R.layout.drawer_header, parent, false);
			return new HeaderViewHolder(view);
		}
		else if(viewType==VIEW_TYPE_MENUITEM)
		{
			View view = inflater.inflate(R.layout.drawer_item, parent, false);
			return new MenuItemViewHolder(view, mListener, mSingleSelector, mImageLoader, mDisplayImageOptions, mImageLoadingListener);
		}
		else
		{
			throw new RuntimeException("There is no view type that matches the type " + viewType);
		}
	}


	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
	{
		if(viewHolder instanceof HeaderViewHolder)
		{
			// render view
			((HeaderViewHolder) viewHolder).bindData();
		}
		else if(viewHolder instanceof MenuItemViewHolder)
		{
			// entity
			MenuItem menuItem = mMenuItemList.get(getMenuItemPosition(position));

			// render view
			if(menuItem != null)
			{
				((MenuItemViewHolder) viewHolder).bindData(menuItem);
			}
		}
	}


	@Override
	public int getItemCount()
	{
		int size = getHeaderCount();
		size += getMenuItemCount();
		return size;
	}


	@Override
	public int getItemViewType(int position)
	{
		int headers = getHeaderCount();
		int menuItemCount = getMenuItemCount();

		if(position < headers) return VIEW_TYPE_HEADER;
		else if(position < headers+menuItemCount) return VIEW_TYPE_MENUITEM;
		else return -1;
	}


	public int getHeaderCount()
	{
		return 1;
	}


	public int getMenuItemCount()
	{
		if(mMenuItemList!=null) return mMenuItemList.size();
		return 0;
	}


	public int getHeaderPosition(int recyclerPosition)
	{
		return recyclerPosition;
	}


	public int getMenuItemPosition(int recyclerPosition)
	{
		return recyclerPosition - getHeaderCount();
	}


	public int getRecyclerPositionByHeader(int headerPosition)
	{
		return headerPosition;
	}


	public int getRecyclerPositionByMenuItem(int menuItemPosition)
	{
		return menuItemPosition + getHeaderCount();
	}


	public void refill(List<MenuItem> menuItemList, IOnItemClickListener listener)
	{
		mMenuItemList = menuItemList;
		mListener = listener;
		notifyDataSetChanged();
	}


	public void stop()
	{

	}


	public void setSelected(int position)
	{
		mSingleSelector.setSelected(position, 0, true);
	}


	public static final class HeaderViewHolder extends RecyclerView.ViewHolder
	{
		public HeaderViewHolder(View itemView)
		{
			super(itemView);
		}


		public void bindData()
		{
			// do nothing
		}
	}


}
