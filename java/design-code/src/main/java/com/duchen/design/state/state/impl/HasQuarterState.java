package com.duchen.design.state.state.impl;

import com.duchen.design.state.GumballMachine;
import com.duchen.design.state.state.State;

import java.util.Random;

public class HasQuarterState implements State {

    private Random mRandomWinner = new Random(System.currentTimeMillis());
    private GumballMachine mGumballMachine;

    public HasQuarterState(GumballMachine gumballMachine) {
        mGumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You can't insert another quarter");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Quarter returned");
        mGumballMachine.setState(mGumballMachine.getNoQuarterState());
    }

    @Override
    public boolean turnCrank() {
        System.out.println("You turned...");
        int winner = mRandomWinner.nextInt(10);
        if ((winner == 0) && mGumballMachine.isAbleToWin()) {
            mGumballMachine.setState(mGumballMachine.getWinnerState());
        } else {
            mGumballMachine.setState(mGumballMachine.getSoldState());
        }
        return true;
    }

    @Override
    public void refill() {
        System.out.println("Refill success!");
    }

    @Override
    public void dispense() {
        System.out.println("No gumball dispensed");
    }

    @Override
    public String toString() {
        return "HAS QUARTER";
    }
}
