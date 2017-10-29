package com.duchen.template.usage.TestAnnotation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duchen.annotation.*;
import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;

@ContentView(R.layout.activity_test_annotation)
public class AnnotationActivity extends AppActivityBase {

    @BindView(R.id.txt_test)
    public TextView mTextView;

    @BindView(R.id.img_test)
    public ImageView mImageView;

    @Override
    public void handleClick(int id, View v) {
    }

    @Override
    public void setContentView() {
        ViewInjector.injectView(this);
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        mTextView.setText("Success");
    }
}
