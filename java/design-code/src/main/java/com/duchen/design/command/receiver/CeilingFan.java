package com.duchen.design.command.receiver;

public class CeilingFan {

    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;

    int mSpeed;

    public CeilingFan() {
        mSpeed = OFF;
    }

    public void high() {
        mSpeed = HIGH;
    }

    public void medium() {
        mSpeed = MEDIUM;
    }

    public void low() {
        mSpeed = LOW;
    }

    public void off() {
        mSpeed = OFF;
    }

    public int getSpeed() {
        return mSpeed;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " speed is " + mSpeed;
    }
}
