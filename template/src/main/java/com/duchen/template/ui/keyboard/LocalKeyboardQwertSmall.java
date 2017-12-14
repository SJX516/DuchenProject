package com.duchen.template.ui.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.duchen.template.R;


/**
 * Created by Duchen on 2017/11/17.
 *
 * 移动POS App 内使用的键盘控件
 */

public class LocalKeyboardQwertSmall extends RelativeLayout implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView4Qwert;
    private KeyboardView keyboardView4Punctuation;
    private Keyboard keyboard4Qwert; // 字母键盘
    private Keyboard keyboard4Punctuation; // 符号键盘
    private boolean isShowing = false;
    private boolean caps = false;
    private boolean qwert = true;

    private KeyboardListener mListener;

    public LocalKeyboardQwertSmall(Context context) {
        this(context, null);
    }

    public LocalKeyboardQwertSmall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalKeyboardQwertSmall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.keyboard_layout_qwert, this);
        initViews();
    }

    protected void initViews() {
        keyboard4Qwert = new Keyboard(getContext(), R.xml.keyboard_qwert);
        keyboard4Punctuation = new Keyboard(getContext(), R.xml.keyboard_punctuation);

        keyboardView4Qwert = (KeyboardView) findViewById(R.id.keyboard_view_4_qwert);
        keyboardView4Qwert.setKeyboard(keyboard4Qwert);
        keyboardView4Qwert.setEnabled(true);
        keyboardView4Qwert.setPreviewEnabled(false);
        keyboardView4Qwert.setOnKeyboardActionListener(this);

        keyboardView4Punctuation = (KeyboardView) findViewById(R.id.keyboard_view_4_punctuation);
        keyboardView4Punctuation.setKeyboard(keyboard4Punctuation);
        keyboardView4Punctuation.setEnabled(true);
        keyboardView4Punctuation.setPreviewEnabled(false);
        keyboardView4Punctuation.setOnKeyboardActionListener(this);
    }

    public void show(EditText editText) {
        show(new EditTextKeyboardListener(editText));
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
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            caps = !caps;
            keyboard4Qwert.setShifted(caps);
            keyboardView4Qwert.invalidateAllKeys();
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
            if (qwert) {
                qwert = false;
                keyboardView4Qwert.setVisibility(GONE);
                keyboardView4Punctuation.setVisibility(VISIBLE);
            } else {
                qwert = true;
                keyboardView4Qwert.setVisibility(VISIBLE);
                keyboardView4Punctuation.setVisibility(GONE);
            }
        } else { // 输入键盘值
            if (caps) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
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
}
