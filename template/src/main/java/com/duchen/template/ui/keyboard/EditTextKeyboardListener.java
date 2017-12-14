package com.duchen.template.ui.keyboard;

import android.text.Editable;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextKeyboardListener implements KeyboardListener {

    private EditText mEditText;

    public EditTextKeyboardListener(EditText editText) {
        mEditText = editText;
    }

    @Override
    public void onShow() {
    }

    @Override
    public void onHide() {

    }

    @Override
    public void onKey(CharSequence text) {
        Editable editable = mEditText.getText();
        int start = mEditText.getSelectionStart();
        editable.insert(start, text);
    }

    @Override
    public boolean onConfirm() {
        mEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        return true;
    }

    @Override
    public void onDelete() {
        Editable editable = mEditText.getText();
        int start = mEditText.getSelectionStart();
        if (editable != null && editable.length() > 0) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }

    @Override
    public void onClear() {
        Editable editable = mEditText.getText();
        editable.clear();
    }
}
