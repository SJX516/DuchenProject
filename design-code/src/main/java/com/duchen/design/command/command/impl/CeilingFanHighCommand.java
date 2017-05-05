package com.duchen.design.command.command.impl;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.receiver.CeilingFan;

public class CeilingFanHighCommand implements Command {

    CeilingFan mCeilingFan;
    int mPrevSpeed;

    public CeilingFanHighCommand(CeilingFan ceilingFan) {
        mCeilingFan = ceilingFan;
    }

    @Override
    public void execute() {
        mPrevSpeed = mCeilingFan.getSpeed();
        switch (mPrevSpeed) {
            case CeilingFan.OFF:
                mCeilingFan.low();
                break;
            case CeilingFan.LOW:
                mCeilingFan.medium();
                break;
            case CeilingFan.MEDIUM:
                mCeilingFan.high();
                break;
            case CeilingFan.HIGH:
                break;
        }
        System.out.println(mCeilingFan);
    }

    @Override
    public void undo() {
        switch (mPrevSpeed) {
            case CeilingFan.OFF:
                mCeilingFan.off();
                break;
            case CeilingFan.LOW:
                mCeilingFan.low();
                break;
            case CeilingFan.MEDIUM:
                mCeilingFan.medium();
                break;
            case CeilingFan.HIGH:
                mCeilingFan.high();
                break;
        }
        System.out.println(mCeilingFan);
    }
}
