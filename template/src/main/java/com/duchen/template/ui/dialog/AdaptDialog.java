package com.duchen.template.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


public class AdaptDialog implements View.OnClickListener {
    private Context mContext;
    private AlertDialog alertDialog;
    private Window window;
    private final SparseArray<View> views;


    /**
     * @param context
     * @param res     注意，像这种动态生成的view（类似Listview中的Item），不应该使用属性
     *                layout_marginBottom 这个属性来设置到底部的间距，这个属性会被应用到父布局中，
     *                使得底部不对齐，应该用 paddingBottom 替换
     *                若 res 的宽或高是被布局文件所固定的，那么 setSize 超过了它的大小的话，会出现留空
     */
    public AdaptDialog(Context context, int res) {

        this.mContext = context;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();

        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        window = alertDialog.getWindow();
        window.setContentView(res);
        views = new SparseArray<>();
    }

    /**
     * An alpha value to apply to this entire window.
     * An alpha of 1.0 means fully opaque and 0.0 means fully transparent
     */
    public void setAlpha(float alpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
    }


    /**
     * 设置窗口的大小
     *
     * @param width  可以使用 {@link ViewGroup.LayoutParams#MATCH_PARENT }
     * @param height {@link ViewGroup.LayoutParams#WRAP_CONTENT } 或者使用指定像素大小
     */
    public void setSize(int width, int height) {
        window.setLayout(width, height);
    }

    /**
     * gravity 可以单独使用一个，也可以 Gravity.LEFT | Gravity.TOP 这样使用两个属性，表示位于左上角
     *
     * @param gravity
     */
    public void setWindowGravity(int gravity) {
        window.setGravity(gravity);
    }

    /**
     * 以下是一套从底部进入，退出的Animation文件，传值 R.style.BottomDialogAnimation
     * --------------- style.xml
     * <?xml version="1.0" encoding="utf-8"?>
     * <resources>
     * <style name="BottomDialogAnimation" parent="android:Animation">
     * <item name="@android:windowEnterAnimation">@anim/dialog_bottom_enter</item>
     * <item name="@android:windowExitAnimation">@anim/dialog_bottom_exit</item>
     * </style>
     * </resources>
     * <p/>
     * --------------- dialog_bottom_enter.xml
     * <?xml version="1.0" encoding="utf-8"?>
     * <set xmlns:android="http://schemas.android.com/apk/res/android" >
     * <translate
     * android:duration="600"
     * android:fromYDelta="100%p"/>
     * </set>
     * <p/>
     * --------------- dialog_bottom_exit.xml
     * <?xml version="1.0" encoding="utf-8"?>
     * <set xmlns:android="http://schemas.android.com/apk/res/android" >
     * <translate
     * android:duration="600"
     * android:toYDelta="100%p" />
     * </set>
     *
     * @param animId
     */
    public void setWindowAnim(int animId) {
        window.setWindowAnimations(animId);
    }

    /**
     * 使用以下设置对话框位置时，要确保和 setWindowGravity 是成套使用的，
     *
     * @param v
     * @see #setBelowView(View)   Gravity.TOP 一起表示将对话框设置于该View之下
     * @see #setAboveView(View)   Gravity.BOTTOM 一起表示将对话框设置于该View之上
     * @see #setToRightOfView(View)    Gravity.LEFT 一起表示将对话框设置于该View的右边
     * @see #setToLeftOfView(View)   Gravity.RIGHT 一起表示将对话框设置于该View的左边
     */
    public void setBelowView(View v) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = (int) v.getY() + v.getHeight();
        window.setAttributes(lp);
    }

    public void setAboveView(View v) {
        Rect rect = new Rect();
        ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int height = rect.height();

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = height - (int) v.getY();
        window.setAttributes(lp);
    }

    public void setToRightOfView(View v) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = (int) v.getX() + v.getWidth();
        window.setAttributes(lp);
    }

    public void setToLeftOfView(View v) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = width - (int) v.getX();
        window.setAttributes(lp);
    }

    public void setViewClickListener(int viewId, OnAdaptViewClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(listener);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        ((OnAdaptViewClickListener) v.getTag()).onAdaptViewClick(alertDialog, v);
    }

    public interface OnAdaptViewClickListener {
        void onAdaptViewClick(AlertDialog dialog, View view);
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = window.findViewById(viewId);
            if (view != null) {
                views.put(viewId, view);
            }
        }
        return (T) view;
    }
}
