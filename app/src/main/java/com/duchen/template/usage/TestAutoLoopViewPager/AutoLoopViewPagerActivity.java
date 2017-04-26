package com.duchen.template.usage.TestAutoLoopViewPager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duchen.template.ui.CustomViewPager;
import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;

/**
 * 实现这个循环的几个要点
 *
 *  1. adapter的getCount里面返回实际数量的两倍
 *  2. 在adapter的finishUpdate中,如果当前是第0个,那就跳到第3个,如果是第5个,那就跳到第2个,这样才可以保证不论在哪都可以左右滑动
 *  3. mViewPager.setOffscreenPageLimit(mAdapter.getCount()); 记得修改viewPager的默认缓存数量,避免因为destroyItem导致的
 *     在最后一个滑动错误的问题
 */
public class AutoLoopViewPagerActivity extends AppActivityBase implements ViewPager.OnPageChangeListener {

    private static int AUTO_SCROLL_TIME = 3000;

    private TestIndicator mIndicator;
    private CustomViewPager mViewPager;
    private TestPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_auto_loop_viewpager);
        findViews();
        initViews();
    }

    private void findViews() {
        mViewPager = (CustomViewPager) findViewById(R.id.view_pager);
        mIndicator = (TestIndicator) findViewById(R.id.indicator);
        mAdapter = new TestPageAdapter();
    }

    private void initViews() {
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
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
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void handleClick(int id, View v) {

    }

    private class TestPageAdapter extends PagerAdapter {

        private int[] mColorIds = new int[] {R.color.red, R.color.blue, R.color.green};

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = convertToRealPosition(position);
            View root = LayoutInflater.from(container.getContext()).inflate(R.layout.item_test,
                    container, false);
            ((ImageView) root.findViewById(R.id.img)).setBackgroundResource(mColorIds[position]);
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int getRealCount() {
            return mColorIds.length;
        }

        @Override
        public int getCount() {
            return mColorIds.length * 2;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mViewPager.getCurrentItem();
            if (position == 0) {
                position = getRealCount();
                mViewPager.setCurrentItem(position, false);
            } else if (position == getCount() - 1) {
                position = getRealCount() - 1;
                mViewPager.setCurrentItem(position, false);
            }
        }

        private int convertToRealPosition(int position) {
            return position % getRealCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
