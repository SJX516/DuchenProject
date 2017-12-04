package com.duchen.design.adapter;

import com.duchen.design.adapter.duck.SimpleDuck;
import com.duchen.design.adapter.turkey.impl.WildTurkey;

public class AdapterTest implements Runnable {

    @Override
    public void run() {
        WildTurkey wildTurkey = new WildTurkey();
        SimpleDuck turkeyAdapter = new TurkeyAdapter(wildTurkey);
        System.out.println("\nThe Turkey says...");
        wildTurkey.gobble();
        wildTurkey.fly();
        System.out.println("\nThe Duck says...");
        turkeyAdapter.quack();
        turkeyAdapter.fly();
    }
}
