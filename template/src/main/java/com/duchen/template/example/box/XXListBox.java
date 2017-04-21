package com.duchen.template.example.box;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.duchen.template.R;
import com.duchen.template.concept.IBox;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.box.helper.XXItemDecoration;
import com.duchen.template.example.box.model.YYItemData;
import com.duchen.template.example.box.model.ZZItemData;
import java.util.List;


public class XXListBox extends RecyclerView implements IBox<XXListBox.ViewModel> {

    public interface ViewModel extends IViewModel {
        List<IViewModel> getItems();
        boolean canLoadMore();
        boolean canPullToRefresh();
    }

    public interface OnClickListener {

        void onClickInside(IViewModel itemData);

        void onClickYYItem(YYItemData itemData);

        void onClickZZItem(ZZItemData itemData);
    }

    private ViewModel mModel;
    private ListBoxAdapter mAdapter;
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
        if (mAdapter == null) {
            mAdapter = new ListBoxAdapter(mModel.getItems());
            this.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(mModel.getItems());
        }
    }

    @Override
    public void update() {
        mAdapter.notifyDataSetChanged();
    }

    public void setOnClickListener(XXListBox.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecoration(new XXItemDecoration(getResources()));
        mAdapter = new ListBoxAdapter(null);
        mInsideItemClickListener = new YYItemBox.OnClickListener() {
            @Override
            public void onClickInsideItem(YYItemData itemData) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClickInside(itemData);
                }
            }
        };
    }

    public class ListBoxAdapter extends RecyclerView.Adapter<ListBoxAdapter.BoxViewHolder> implements View
            .OnClickListener {

        public static final int TYPE_VOID = 0;
        public static final int TYPE_YY = 1;
        public static final int TYPE_ZZ = 2;

        private List<IViewModel> mItems;


        public ListBoxAdapter(List<IViewModel> items) {
            mItems = items;
        }

        public void updateData(List<IViewModel> items) {
            mItems = items;
        }

        @Override
        public BoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemBox = null;
            switch (viewType) {
                case TYPE_YY:
                    itemBox = new YYItemBox(getContext());
                    ((YYItemBox)itemBox).setInsideItemClickListener(mInsideItemClickListener);
                    break;
                case TYPE_ZZ:
                    itemBox = new ZZItemBox(getContext());
                    break;
            }
            if (itemBox != null) {
                itemBox.setOnClickListener(this);
                return new BoxViewHolder(itemBox);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (mItems == null || mItems.size() <= position || mItems.get(position) == null) {
                return TYPE_VOID;
            }
            IViewModel viewModel = mItems.get(position);
            if (viewModel instanceof YYItemData) {
                return TYPE_YY;
            } else if (viewModel instanceof ZZItemData) {
                return TYPE_ZZ;
            }
            return TYPE_VOID;
        }

        @Override
        public void onBindViewHolder(BoxViewHolder holder, int position) {
            if (holder == null || mItems == null || mItems.size() <= position) {
                return;
            }
            holder.mBox.bindViewModel(mItems.get(position));
            ((View)holder.mBox).setTag(mItems.get(position));
            holder.mBox.update();
        }

        @Override
        public int getItemCount() {
            if (mItems == null) {
                return 0;
            } else {
                return mItems.size();
            }
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

        class BoxViewHolder extends RecyclerView.ViewHolder {

            IBox mBox;
            public BoxViewHolder(View itemView) {
                super(itemView);
                mBox = (IBox) itemView;
            }
        }
    }
}