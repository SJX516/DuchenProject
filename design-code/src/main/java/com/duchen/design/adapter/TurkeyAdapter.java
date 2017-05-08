package com.duchen.design.adapter;


import com.duchen.design.adapter.duck.SimpleDuck;
import com.duchen.design.adapter.turkey.Turkey;

public class TurkeyAdapter implements SimpleDuck {

    private Turkey mTurkey;

    public TurkeyAdapter(Turkey turkey) {
        mTurkey = turkey;
    }

    @Override
    public void quack() {
        mTurkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 5; i++) {
            mTurkey.fly();
        }
    }
}
