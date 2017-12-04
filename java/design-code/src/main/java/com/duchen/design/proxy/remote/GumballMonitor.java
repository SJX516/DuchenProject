package com.duchen.design.proxy.remote;

public class GumballMonitor {

    private GumballMachineRemote mGumballMachineRemote;

    public GumballMonitor(GumballMachineRemote gumballMachineRemote) {
        mGumballMachineRemote = gumballMachineRemote;
    }

    public void report() {
        System.out.println(mGumballMachineRemote.getDesc());
    }
}
