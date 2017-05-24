package com.duchen.design.proxy;

import com.duchen.design.proxy.protect.Person;
import com.duchen.design.proxy.protect.PersonImpl;
import com.duchen.design.proxy.remote.GumballMonitor;
import com.duchen.design.proxy.remote.proxy.Naming;
import com.duchen.design.proxy.virtual.TextProxy;
import com.duchen.design.state.GumballMachine;

public class ProxyTest implements Runnable {

    @Override
    public void run() {
        System.out.println("--------REMOTE PROXY--------");
        GumballMachine machine1 = new GumballMachine("num1", 20);
        GumballMachine machine2 = new GumballMachine("num2", 10);
        GumballMachine machine3 = new GumballMachine("num3", 40);
        Naming.rebind(machine1.getLocation(), machine1);
        Naming.rebind(machine2.getLocation(), machine2);
        Naming.rebind(machine3.getLocation(), machine3);
        GumballMonitor monitor1 = new GumballMonitor(Naming.lookup("num1"));
        GumballMonitor monitor2 = new GumballMonitor(Naming.lookup("num2"));
        GumballMonitor monitor3 = new GumballMonitor(Naming.lookup("num3"));
        monitor1.report();
        monitor2.report();
        monitor3.report();

        System.out.println("\n--------DYNAMIC PROXY--------");
        Person joe = new PersonImpl("Joe", "bowling, Go");
        Person ownerProxy = PersonImpl.getOwnerProxy(joe);
        System.out.println(ownerProxy);
        try {
            ownerProxy.setInterests("no bowling");
            ownerProxy.addRating(10);
        } catch (Exception e) {
            System.out.println("Can't set rating from owner proxy");
        }
        System.out.println(ownerProxy);

        Person nonOwnerProxy = PersonImpl.getNonOwnerProxy(joe);
        System.out.println(nonOwnerProxy);
        try {
            nonOwnerProxy.addRating(8);
            nonOwnerProxy.setInterests("bowling, Go");
        } catch (Exception e) {
            System.out.println("Can't set interests from non owner proxy");
        }
        System.out.println(nonOwnerProxy);

        System.out.println("\n--------VIRTUAL PROXY--------");
        TextProxy textProxy = new TextProxy();
        textProxy.print();
    }
}
