package com.duchen.design.proxy.virtual;

public class ActualText implements Text {

    private String mText;

    public ActualText(String text) {
        try {
            Thread.sleep(1000);
            mText = text;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(mText);
    }
}
