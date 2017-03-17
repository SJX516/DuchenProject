package com.duchen.template.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.duchen.template.R;
import com.duchen.template.concept.IBox;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.ui.model.YYItemData;
import com.duchen.template.example.ui.model.ZZItemData;
import com.duchen.template.ui.AdapterBase;

import java.util.Collection;


public class XXListBox extends ListView implements IBox<XXListBox.ViewModel> {

    public interface ViewModel extends IViewModel {
        Collection<IViewModel> getItems();
        boolean canLoadMore();
        boolean canPullToRefresh();
    }

    public interface OnClickListener {

        void onClickInside(IViewModel itemData);

        void onClickYYItem(YYItemData itemData);

        void onClickZZItem(ZZItemData itemData);
    }

    private ViewModel mModel;
    private CoursewareAdapter mAdapter;
    private XXListBox.OnClickListener mOnClickListener;
    private YYItemBox.OnClickListener mInsideItemClickListener;

    public XXListBox(Context context) {
        this(context, null, 0);
    }

    public XXListBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXListBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void bindViewModel(ViewModel viewModel) {
        mModel = viewModel;
        mAdapter = new CoursewareAdapter(this.getContext(), mModel);
        this.setAdapter(mAdapter);
    }

    @Override
    public void update() {
        mAdapter.notifyDataSetChanged();
    }

    public void setOnClickListener(XXListBox.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private void init() {
        mInsideItemClickListener = new YYItemBox.OnClickListener() {
            @Override
            public void onClickInsideItem(YYItemData itemData) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClickInside(itemData);
                }
            }
        };
    }

    class CoursewareAdapter extends AdapterBase<ViewModel> implements View.OnClickListener {

        public CoursewareAdapter(Context context, ViewModel dataProvider) {
            super(context, dataProvider);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IViewModel itemData = (IViewModel) mItems.get(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.box_xxlist, null);
            }
            convertView.setTag(itemData);
            convertView.setOnClickListener(this);
            updateItemBox(convertView, itemData);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            IViewModel itemData = null;
            if (v != null && v.getTag() != null) {
                if (v.getTag() instanceof IViewModel) {
                    itemData = (IViewModel) v.getTag();
                }
            }
            if (itemData == null || mOnClickListener == null) {
                return;
            }
            if (itemData instanceof YYItemData) {
                mOnClickListener.onClickYYItem((YYItemData) itemData);
            } else if (itemData instanceof ZZItemData) {
                mOnClickListener.onClickZZItem((ZZItemData) itemData);
            }
        }

        @Override
        protected void buildItem() {
            for (IViewModel itemData : mDataProvider.getItems()) {
                if (isItemNeedShow(itemData)) {
                    mItems.add(itemData);
                }
            }
        }

        private boolean isItemNeedShow(IViewModel itemData) {
            return true;
        }

        private void updateItemBox(final View convertView, final IViewModel itemData) {
            if (convertView == null || itemData == null) {
                return;
            }
            IBox itemBox = null;

            YYItemBox YYItemBox = (YYItemBox) convertView.findViewById(R.id.xxlist_yyitem);
            ZZItemBox ZZItemBox = (ZZItemBox) convertView.findViewById(R.id.xxlist_zzitem);

            if (itemData instanceof YYItemData) {
                YYItemBox.setVisibility(View.VISIBLE);
                ZZItemBox.setVisibility(View.GONE);
                YYItemBox.setInsideItemClickListener(mInsideItemClickListener);
                itemBox = YYItemBox;
            } else if (itemData instanceof ZZItemData) {
                ZZItemBox.setVisibility(View.VISIBLE);
                YYItemBox.setVisibility(View.GONE);
                itemBox = YYItemBox;
            }

            if (itemBox != null) {
                itemBox.bindViewModel(itemData);
                itemBox.update();
            }
        }
    }
}