package com.duchen.template.usage.TestViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

/**
 * 查看ViewPager的源码,验证View移动的几种方式,成果在笔记《View滑动》和《ViewPager源码笔记》中
 */
public class NormalViewPagerActivity extends AppActivityBase implements ViewPager.OnPageChangeListener {

    private View mRoot;
    private ViewPager mPager;
    private DemoCollectionPagerAdapter mPagerAdapter;
    private Button mButton;
    private Button mButton2;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_viewpager);
    }

    @Override
    public void findViews() {
        mRoot = findViewById(R.id.content);
        mPager = (ViewPager) findViewById(R.id.pager_content);
        mButton = (Button) findViewById(R.id.btn_one);
        mButton2 = (Button) findViewById(R.id.btn_two);
    }

    @Override
    public void initViews() {
        mPager.addOnPageChangeListener(this);
        mPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mButton.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mButton.setX(mButton.getLeft() - positionOffsetPixels);
        LogUtil.d("Left: " + mButton.getLeft() + "   X:" + mButton.getX());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // Instances of this class are fragments representing a single
    // object in our collection.
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.frame_test_viewpager, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.text_content)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }

    @Override
    public void handleClick(int id, View v) {
        switch (id) {
            case R.id.btn_one:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mButton.getLayoutParams();
                params.rightMargin += 100;
                mButton.setLayoutParams(params);
                mButton2.setX(mButton2.getX() - 100);
                break;
            case R.id.btn_two:
                mRoot.requestLayout();
                break;
        }
    }
}
