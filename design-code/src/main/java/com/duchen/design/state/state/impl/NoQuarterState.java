package com.duchen.design.state.state.impl;

import com.duchen.design.state.GumballMachine;
import com.duchen.design.state.state.State;

public class NoQuarterState implements State {

    private GumballMachine mGumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        mGumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You Inserted a quarter");
        mGumballMachine.setState(mGumballMachine.getHasQuarterState());
    }

    @Override
    public void ejectQuarter() {
        System.out.println("You haven't inserted a quarter");
    }

    @Override
    public boolean turnCrank() {
        System.out.println("You turned, but there's no quarter");
        return false;
    }

    @Override
    public void dispense() {
        System.out.println("You need to pay first");
    }

    @Override
    public void refill() {
        System.out.println("Refill success!");
    }

    @Override
    public String toString() {
        return "NO QUARTER";
    }
}
