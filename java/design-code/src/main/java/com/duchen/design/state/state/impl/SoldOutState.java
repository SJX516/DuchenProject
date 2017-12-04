package com.duchen.design.state.state.impl;

import com.duchen.design.state.GumballMachine;
import com.duchen.design.state.state.State;

public class SoldOutState implements State {

    private GumballMachine mGumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        mGumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("Gumballs has sold out! Quarter returned");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("You haven't inserted a quarter");
    }

    @Override
    public boolean turnCrank() {
        System.out.println("You haven't inserted a quarter");
        return false;
    }

    @Override
    public void dispense() {
        System.out.println("You haven't inserted a quarter");
    }

    @Override
    public void refill() {
        System.out.println("Refill success!");
        if (mGumballMachine.hasMoreGumball()) {
            mGumballMachine.setState(mGumballMachine.getNoQuarterState());
        }
    }

    @Override
    public String toString() {
        return "SOLD OUT";
    }
}
