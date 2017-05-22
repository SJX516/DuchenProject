package com.duchen.design.state;

import com.duchen.design.state.state.State;
import com.duchen.design.state.state.impl.HasQuarterState;
import com.duchen.design.state.state.impl.NoQuarterState;
import com.duchen.design.state.state.impl.SoldOutState;
import com.duchen.design.state.state.impl.SoldState;
import com.duchen.design.state.state.impl.WinnerState;

public class GumballMachine {

    private State soldOutState;
    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;
    private State winnerState;

    private State mState = soldOutState;
    private int mCount;

    public GumballMachine(int count) {
        mCount = count;
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);
        if (mCount > 0) {
            mState = noQuarterState;
        }
    }

    public void setState(State state) {
        mState = state;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public void insertQuarter() {
        mState.insertQuarter();
    }

    public void ejectQuarter() {
        mState.ejectQuarter();
    }

    public void turnCrank() {
        if (mState.turnCrank()) {
            mState.dispense();
        }
    }

    public void refill(int num) {
        mCount += num;
        mState.refill();
    }

    public boolean isAbleToWin() {
        return mCount > 1;
    }

    public boolean hasMoreGumball() {
        return mCount > 0;
    }

    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (mCount != 0) {
            mCount = mCount - 1;
        }
    }

    @Override
    public String toString() {
        return "Current State: " + mState + "    Gumball Count: " + mCount;
    }
}
