package com.duchen.design;

import com.duchen.design.absFactory.AbsFactoryTest;
import com.duchen.design.adapter.AdapterTest;
import com.duchen.design.command.CommandTest;
import com.duchen.design.composite.CompositeTest;
import com.duchen.design.compound.CompoundTest;
import com.duchen.design.decorator.DecoratorTest;
import com.duchen.design.facade.FacadeTest;
import com.duchen.design.factoryMethod.FactoryMethodTest;
import com.duchen.design.iterator.IteratorTest;
import com.duchen.design.mvc.MVCTest;
import com.duchen.design.observer.ObserverTest;
import com.duchen.design.proxy.ProxyTest;
import com.duchen.design.state.StateTest;
import com.duchen.design.strategy.StrategyTest;
import com.duchen.design.templateMethod.TemplateMethodTest;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER, DECORATOR, FACTORY_METHOD, ABS_FACTORY, SINGLETON, COMMAND, ADAPTER, FACADE,
        TEMPLATE_METHOD, ITERATOR, COMPOSITE, STATE, PROXY, COMPOUND, MVC
    }

    public static void main(String[] args) {
        DesignMain designMain = new DesignMain();
        Runnable testRun = designMain.getRunnable(DesignPattern.MVC);
        testRun.run();
    }

    private Runnable getRunnable(DesignPattern pattern) {
        switch (pattern) {
            case STRATEGY:
                return new StrategyTest();
            case OBSERVER:
                return new ObserverTest();
            case DECORATOR:
                return new DecoratorTest();
            case FACTORY_METHOD:
                return new FactoryMethodTest();
            case ABS_FACTORY:
                return new AbsFactoryTest();
            case COMMAND:
                return new CommandTest();
            case ADAPTER:
                return new AdapterTest();
            case FACADE:
                return new FacadeTest();
            case TEMPLATE_METHOD:
                return new TemplateMethodTest();
            case ITERATOR:
                return new IteratorTest();
            case COMPOSITE:
                return new CompositeTest();
            case STATE:
                return new StateTest();
            case PROXY:
                return new ProxyTest();
            case COMPOUND:
                return new CompoundTest();
            case MVC:
                return new MVCTest();
            default:
                return new StrategyTest();
        }
    }
}
