package com.duchen.template.example.box;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
        //在 recyclerView 中直接使用 new Box 的形式,无法为该 View 的 width 设置正确的值,所以这里需要手动设置
        //或者把当前类改为继承自 RelativeLayout 也可以解决
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
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
