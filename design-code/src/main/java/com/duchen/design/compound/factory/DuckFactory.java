package com.duchen.design.compound.factory;

import com.duchen.design.compound.Quackable;
import com.duchen.design.compound.duck.DuckCall;
import com.duchen.design.compound.duck.MallardDuck;
import com.duchen.design.compound.duck.RedheadDuck;
import com.duchen.design.compound.duck.RubberDuck;

public class DuckFactory extends AbstractDuckFactory {
    @Override
    public Quackable createMallardDuck() {
        return new MallardDuck();
    }

    @Override
    public Quackable createRedheadDuck() {
        return new RedheadDuck();
    }

    @Override
    public Quackable createDuckCall() {
        return new DuckCall();
    }

    @Override
    public Quackable createRubbleDuck() {
        return new RubberDuck();
    }
}
