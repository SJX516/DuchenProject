package com.duchen.design.state.state;

public interface State {
    void insertQuarter();
    void ejectQuarter();
    boolean turnCrank();
    void dispense();
    void refill();
}
