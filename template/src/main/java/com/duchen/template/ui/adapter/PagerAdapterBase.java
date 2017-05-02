package com.duchen.template.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class PagerAdapterBase<T> extends PagerAdapter {

    protected LayoutInflater mInflater;
    protected List<T> mDataList;
    private SparseArray<View> mViewSparseArray;

    public PagerAdapterBase(Context context, List<T> dataList) {
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
        mViewSparseArray = new SparseArray<>(dataList.size());
    }

    @Override
    public int getCount() {
        if (mDataList == null) return 0;
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewSparseArray.get(position);
        if (view == null) {
            view = getView(container, position);
            mViewSparseArray.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewSparseArray.get(position));
    }

    public abstract View getView(ViewGroup container, int position);

}
