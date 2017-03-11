package com.duchen.template.usage.PinScrollable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.duchen.template.ui.AdapterBase;
import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.ToastUtil;

public class PinScrollableActivity extends AppActivityBase implements ScrollableLinearLayout.Callback {

    private ScrollableLinearLayout mScrollableLinearLayout;
    private View mHeaderView;
    private View mHeaderTab;
    private ListView mListView;

    private int mHeaderHeight;

    private boolean mIsFirstListItemVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_scrollable);
        mScrollableLinearLayout = (ScrollableLinearLayout) findViewById(R.id.scrollview);
        mHeaderView = findViewById(R.id.head_view);
        mHeaderTab = findViewById(R.id.head_tab);
        mListView = (ListView) findViewById(R.id.list);

        mScrollableLinearLayout.setCallback(this);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return "" + position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(PinScrollableActivity.this).inflate(R.layout.item_kotlin_forecast, null);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("click " + position);
                    }
                });
                return convertView;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mIsFirstListItemVisible = firstVisibleItem == 0;
            }
        });
    }

    @Override
    public void handleClick(int id, View v) {

    }

    @Override
    public boolean tryScroll(boolean isVerticalMove, float scrollValue) {
        if (mHeaderHeight == 0) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            makeSureContentEnough();
        }
        if (isVerticalMove) {
            ViewGroup.MarginLayoutParams params =  (ViewGroup.MarginLayoutParams) mHeaderView.getLayoutParams();
            int margin = params.topMargin;
            if ((margin - scrollValue) > 0) {
                return false;
            } else if (margin - scrollValue <= -mHeaderHeight) {
                setHeadViewTopMargin(-mHeaderHeight);
                return false;
            } else {
                if (scrollValue > 0 || isListViewShowTop()) {
                    margin -= scrollValue;
                    setHeadViewTopMargin(margin);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private void setHeadViewTopMargin(int topMargin) {
        ViewGroup.MarginLayoutParams params =  (ViewGroup.MarginLayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
    }

    private boolean isListViewShowTop() {
        if (mListView.getChildCount() > 0) {
            return mIsFirstListItemVisible && mListView.getChildAt(0).getTop() == 0;
        }
        return true;
    }

    private void makeSureContentEnough() {
        final int CONTAINER_HEIGHT = mScrollableLinearLayout.getHeight();
        final int HEADER_TAB_HEIGHT = mHeaderTab.getHeight();

        int fragmentHeight = CONTAINER_HEIGHT - HEADER_TAB_HEIGHT;

        if (mListView != null) {
            mListView.getLayoutParams().height = fragmentHeight;
        }
    }
}
