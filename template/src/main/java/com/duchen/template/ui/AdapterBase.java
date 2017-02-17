package com.duchen.template.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Adapter基类
 */

public abstract class AdapterBase<T> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected T mDataProvider;
    protected ArrayList<Object> mItems = new ArrayList<Object>();

    public AdapterBase(Context context, T dataProvider) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataProvider = dataProvider;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract void buildItem();

    @Override
    public void notifyDataSetChanged() {
        mItems.clear();
        buildItem();
        super.notifyDataSetChanged();
    }

    public void invalidate() {
        super.notifyDataSetChanged();
    }
}
