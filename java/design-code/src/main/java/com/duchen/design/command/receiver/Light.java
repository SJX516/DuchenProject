package com.duchen.design.command.receiver;

public class Light {

    private boolean mIsOn = false;

    public void on() {
        mIsOn = true;
    }

    public void off() {
        mIsOn = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " is " + (mIsOn ? "on" : "off" );
    }
}
