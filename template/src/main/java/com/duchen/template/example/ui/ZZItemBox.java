package com.duchen.template.example.ui;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.duchen.template.R;
import com.duchen.template.concept.IBox;
import com.duchen.template.example.ui.model.ZZItemData;

public class ZZItemBox extends FrameLayout implements IBox<ZZItemData> {

    private ZZItemData mItemData;

    private ImageView vImage;

    public ZZItemBox(Context context) {
        super(context);
    }

    public ZZItemBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZZItemBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void bindViewModel(ZZItemData viewModel) {
        mItemData = viewModel;
    }

    @Override
    public void update() {
        if (check()) {
            // TODO 替换成使用url展示图片的方法
            vImage.setImageURI(Uri.parse(mItemData.getImageUrl()));
        }
    }

    public boolean check() {
        return mItemData != null;
    }

    protected void initViews() {
        inflate(this.getContext(), R.layout.box_example_zzitem, this);
        vImage = (ImageView) findViewById(R.id.zzitem_image);
    }
}
