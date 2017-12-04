package com.duchen.design.compound.factory;

import com.duchen.design.compound.QuackCounter;
import com.duchen.design.compound.Quackable;
import com.duchen.design.compound.duck.DuckCall;
import com.duchen.design.compound.duck.MallardDuck;
import com.duchen.design.compound.duck.RedheadDuck;
import com.duchen.design.compound.duck.RubberDuck;

public class CountingDuckFactory extends AbstractDuckFactory {
    @Override
    public Quackable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }

    @Override
    public Quackable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }

    @Override
    public Quackable createDuckCall() {
        return new QuackCounter(new DuckCall());
    }

    @Override
    public Quackable createRubbleDuck() {
        return new QuackCounter(new RubberDuck());
    }
}
