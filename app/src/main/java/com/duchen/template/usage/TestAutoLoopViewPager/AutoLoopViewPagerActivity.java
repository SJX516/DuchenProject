package com.duchen.template.usage.TestAutoLoopViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duchen.template.ui.CustomViewPager;
import com.duchen.template.ui.adapter.PagerAdapterBase;
import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实现这个循环的几个要点
 *
 *  1. adapter的getCount里面返回实际数量的两倍
 *  2. 在viewPager的onPageScrollStateChanged中,如果当前是第0个,那就跳到第3个,如果是第5个,那就跳到第2个,这样才可以保证不论在哪都可以左右滑动
 */
public class AutoLoopViewPagerActivity extends AppActivityBase implements ViewPager.OnPageChangeListener {

    private static int AUTO_SCROLL_TIME = 3000;

    private TestIndicator mIndicator;
    private CustomViewPager mViewPager;
    private TestPageAdapter mAdapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_auto_loop_viewpager);
    }

    @Override
    public void findViews() {
        mViewPager = (CustomViewPager) findViewById(R.id.pager_content);
        mIndicator = (TestIndicator) findViewById(R.id.indicator_bottom);
        mAdapter = new TestPageAdapter(this, new ArrayList<>(Arrays.asList(R.color.red, R.color.blue, R.color.green)));
    }

    @Override
    public void initViews() {
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(this);
        if (mAdapter.getCount() <= 1) {
            mIndicator.setVisibility(View.GONE);
        } else {
            mIndicator.setVisibility(View.VISIBLE);
        }
        mIndicator.setCount(mAdapter.getRealCount());
        mIndicator.setCurrentItem(0);
        initAutoScroll();
    }

    private Runnable mAutoRunnable = new Runnable() {
        @Override
        public void run() {
            scrollToNextPage();
        }
    };

    private void initAutoScroll() {
        mHandler.removeCallbacks(mAutoRunnable);
        mHandler.postDelayed(mAutoRunnable, AUTO_SCROLL_TIME);
    }

    private void scrollToNextPage() {
        int nextPosition = mViewPager.getCurrentItem() + 1;
        mViewPager.setCurrentItem(nextPosition % mAdapter.getCount(), true);
    }


    @Override
    public void onPageSelected(int position) {
        mIndicator.setCurrentItem(position % mAdapter.getRealCount());
        mHandler.removeCallbacks(mAutoRunnable);
        mHandler.postDelayed(mAutoRunnable, AUTO_SCROLL_TIME);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int position = mViewPager.getCurrentItem();
            if (position == 0) {
                position = mAdapter.getRealCount();
                mViewPager.setCurrentItem(position, false);
            } else if (position == mAdapter.getCount() - 1) {
                position = mAdapter.getRealCount() - 1;
                mViewPager.setCurrentItem(position, false);
            }
        }
    }

    @Override
    public void handleClick(int id, View v) {

    }

    private class TestPageAdapter extends PagerAdapterBase<Integer> {

        public TestPageAdapter(Context context, List<Integer> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            View root = mInflater.inflate(R.layout.item_test, container, false);
            ((ImageView) root.findViewById(R.id.img_item)).setBackgroundResource(mDataList.get(convertToRealPosition(position)));
            return root;
        }

        public int getRealCount() {
            return mDataList.size();
        }

        @Override
        public int getCount() {
            return getRealCount() * 2;
        }

        private int convertToRealPosition(int position) {
            return position % getRealCount();
        }
    }
}
