package com.duchen.design.command.command.impl;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.receiver.CeilingFan;

public class CeilingFanLowCommand implements Command {

    CeilingFan mCeilingFan;
    int mPrevSpeed;

    public CeilingFanLowCommand(CeilingFan ceilingFan) {
        mCeilingFan = ceilingFan;
    }

    @Override
    public void execute() {
        mPrevSpeed = mCeilingFan.getSpeed();
        switch (mPrevSpeed) {
            case CeilingFan.OFF:
                break;
            case CeilingFan.LOW:
                mCeilingFan.off();
                break;
            case CeilingFan.MEDIUM:
                mCeilingFan.low();
                break;
            case CeilingFan.HIGH:
                mCeilingFan.medium();
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
