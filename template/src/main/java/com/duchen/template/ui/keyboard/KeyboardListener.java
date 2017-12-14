package com.duchen.template.ui.keyboard;

public interface KeyboardListener {

    void onShow();

    void onHide();

    void onKey(CharSequence text);

    boolean onConfirm();

    void onDelete();

    void onClear();
}
