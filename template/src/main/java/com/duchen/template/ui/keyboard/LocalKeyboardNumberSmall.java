package com.duchen.template.ui.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.duchen.template.R;


/**
 * 数字小键盘
 */

public class LocalKeyboardNumberSmall extends RelativeLayout implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView4Number;
    private Keyboard keyboardNumber; // 字母键盘
    private boolean isShowing = false;

    private KeyboardListener mListener;

    public LocalKeyboardNumberSmall(Context context) {
        this(context, null);
    }

    public LocalKeyboardNumberSmall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalKeyboardNumberSmall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.keyboard_layout_number, this);
        initViews();
    }

    protected void initViews() {
        keyboardNumber = new Keyboard(getContext(), R.xml.keyboard_number);
        keyboardView4Number = (KeyboardView) findViewById(R.id.keyboard_view_4_number);
        keyboardView4Number.setKeyboard(keyboardNumber);
        keyboardView4Number.setEnabled(true);
        keyboardView4Number.setPreviewEnabled(false);
        keyboardView4Number.setOnKeyboardActionListener(this);
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 显示键盘
     */
    public void show(KeyboardListener listener) {
        if (mListener != null && mListener != listener) {
            mListener.onHide();
        }
        this.mListener = listener;
        if (!isShowing) {
                mListener.onShow();
                setVisibility(VISIBLE);
            isShowing = true;
        }
    }


    /**
     * 隐藏键盘，请注意 Hide 是 Invisible
     */
    public void hide() {
        if (isShowing) {
                mListener.onHide();
                setVisibility(INVISIBLE);
            isShowing = false;
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        dispatchKey(primaryCode);
    }

    private void dispatchKey(int primaryCode) {
        if (mListener == null) {
            return;
        }

        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            hide();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            mListener.onDelete();
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {
            if (mListener.onConfirm()) {
                hide();
            }
        } else if (primaryCode == Keyboard.KEYCODE_ALT) {
            mListener.onClear();
        } else { // 输入键盘值
            mListener.onKey(Character.toString((char) primaryCode));
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
