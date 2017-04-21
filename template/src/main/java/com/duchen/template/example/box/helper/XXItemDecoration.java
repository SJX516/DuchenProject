package com.duchen.template.example.box.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duchen.template.R;
import com.duchen.template.example.box.XXListBox;
import com.duchen.template.utils.DensityUtil;

public class XXItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final int mThinMarginHorizontal;
    private final int mThinHeight;
    private final int mThickHeight;

    public XXItemDecoration(Resources resources) {
        mDivider = new ColorDrawable(resources.getColor(R.color.color_divider));
        mThinMarginHorizontal = DensityUtil.dp2px(16);
        mThinHeight = 1;
        mThickHeight = DensityUtil.dp2px(8);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,right,top,bottom;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            DividerDesc dividerDesc = getDividerDesc(child, parent);
            if (dividerDesc.needMargin) {
                left += mThinMarginHorizontal;
                right -= mThinMarginHorizontal;
            }

            top = child.getBottom() + params.bottomMargin;
            bottom = top + dividerDesc.dividerHeight;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, getDividerDesc(view, parent).dividerHeight);
    }

    private DividerDesc getDividerDesc(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        switch (parent.getAdapter().getItemViewType(position)) {
            case XXListBox.ListBoxAdapter.TYPE_YY:
                return new DividerDesc(0, false);
            case XXListBox.ListBoxAdapter.TYPE_ZZ:
                int nextItemType = parent.getAdapter().getItemViewType(position + 1);
                if (nextItemType == XXListBox.ListBoxAdapter.TYPE_YY) {
                    return new DividerDesc(mThickHeight, false);
                } else if (nextItemType == XXListBox.ListBoxAdapter.TYPE_ZZ) {
                    return new DividerDesc(mThinHeight, true);
                }
            default:
                return new DividerDesc(0, false);
        }
    }

    private class DividerDesc {
        private int dividerHeight = mThinHeight;
        private boolean needMargin = true;

        public DividerDesc(int dividerHeight, boolean needMargin) {
            this.dividerHeight = dividerHeight;
            this.needMargin = needMargin;
        }
    }
}
