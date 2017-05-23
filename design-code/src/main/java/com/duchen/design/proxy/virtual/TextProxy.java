package com.duchen.design.proxy.virtual;

public class TextProxy implements Text {

    ActualText mActualText;
    Thread mThread;
    boolean mIsThreadRunning = false;


    @Override
    public void print() {
        if (mActualText != null) {
            mActualText.print();
        } else {
            System.out.println("Proxy Text");
            if (!mIsThreadRunning) {
                mIsThreadRunning = true;
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mActualText = new ActualText("Actual Text");
                        print();
                    }
                });
                mThread.start();
            }
        }
    }
}
