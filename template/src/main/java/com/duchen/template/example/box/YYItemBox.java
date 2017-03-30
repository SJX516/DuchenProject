package com.duchen.template.example.box;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.duchen.template.R;
import com.duchen.template.concept.IBox;
import com.duchen.template.example.box.model.YYItemData;


public class YYItemBox extends FrameLayout implements IBox<YYItemData>, View.OnClickListener {

    public interface OnClickListener {
        void onClickInsideItem(YYItemData itemData);
    }

    private YYItemData mItemData;

    private TextView vTitle;
    private TextView vDescription;
    private View vSomething;
    private OnClickListener mInsideItemClickListener;


    public YYItemBox(Context context) {
        super(context);
    }

    public YYItemBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YYItemBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    protected void initViews() {
        inflate(this.getContext(), R.layout.box_example_yyitem, this);
        vTitle = (TextView) findViewById(R.id.yyitem_title);
        vDescription = (TextView) findViewById(R.id.yyitem_description);
        vSomething = findViewById(R.id.yyitem_something);
        vSomething.setOnClickListener(this);
    }

    public void setInsideItemClickListener(OnClickListener listener) {
        mInsideItemClickListener = listener;
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void bindViewModel(YYItemData viewModel) {
        mItemData = viewModel;
    }

    @Override
    public void update() {
        if (check()) {
            vTitle.setText(mItemData.getTitle());
            vDescription.setText(mItemData.getDescription());
            vSomething.setVisibility(mItemData.isShowSomething() ? VISIBLE : GONE);
            setBackgroundResource(mItemData.isSelected() ? R.color.color_item_selected_bg : R.color.color_item_bg);
        }
    }


    @Override
    public void onClick(View v) {
        if (v != null && v.equals(vSomething) && mInsideItemClickListener != null && check()) {
            mInsideItemClickListener.onClickInsideItem(mItemData);
        }
    }

    public boolean check() {
        return mItemData != null;
    }

}
