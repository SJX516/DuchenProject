package com.duchen.design.proxy.remote;

public class GumballMonitor {

    private com.duchen.design.proxy.remote.GumballMachineRemote mGumballMachineRemote;

    public GumballMonitor(com.duchen.design.proxy.remote.GumballMachineRemote gumballMachineRemote) {
        mGumballMachineRemote = gumballMachineRemote;
    }

    public void report() {
        System.out.println(mGumballMachineRemote.getDesc());
    }
}
