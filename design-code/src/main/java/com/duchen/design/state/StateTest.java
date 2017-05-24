package com.duchen.design.state;

public class StateTest implements Runnable {

    @Override
    public void run() {
        GumballMachine gumballMachine = new GumballMachine("test1", 1);
        System.out.println(gumballMachine);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.refill(2);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        System.out.println(gumballMachine);
    }
}
