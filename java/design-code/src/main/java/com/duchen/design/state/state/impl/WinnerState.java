package com.duchen.design.state.state.impl;

import com.duchen.design.state.GumballMachine;
import com.duchen.design.state.state.State;

public class WinnerState implements State {

    private GumballMachine mGumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
        mGumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("Please wait, we're already giving you a gumball");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("sorry, you already turned the crank");
    }

    @Override
    public boolean turnCrank() {
        System.out.println("Turning twice doesn't get you another gumball!");
        return false;
    }

    @Override
    public void refill() {
        System.out.println("Refill success!");
    }

    @Override
    public void dispense() {
        System.out.println("YOU'RE A WINNER! You get two gumballs for your quarter");
        mGumballMachine.releaseBall();
        mGumballMachine.releaseBall();
        if (mGumballMachine.hasMoreGumball()) {
            mGumballMachine.setState(mGumballMachine.getNoQuarterState());
        } else {
            System.out.println("Oops, out of gumballs!");
            mGumballMachine.setState(mGumballMachine.getSoldOutState());
        }
        System.out.println("");
    }

    @Override
    public String toString() {
        return "WINNER";
    }
}
