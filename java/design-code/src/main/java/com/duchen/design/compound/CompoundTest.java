package com.duchen.design.compound;

import com.duchen.design.compound.factory.AbstractDuckFactory;
import com.duchen.design.compound.factory.CountingDuckFactory;
import com.duchen.design.compound.goose.Goose;
import com.duchen.design.compound.goose.GooseAdapter;
import com.duchen.design.compound.observer.Quackologist;

public class CompoundTest implements Runnable {

    @Override
    public void run() {
        System.out.println("\nDuck Simulator");
        AbstractDuckFactory duckFactory = new CountingDuckFactory();
        simulate(duckFactory);
        System.out.println("\nThe ducks quacked " + QuackCounter.getCount() + " times");
    }

    void simulate(AbstractDuckFactory duckFactory) {
        Flock flockOfDucks = new Flock();
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubbleDuck();
        Quackable gooseDuck = new GooseAdapter(new Goose());
        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(rubberDuck);
        flockOfDucks.add(gooseDuck);

        Flock flockOfMallards = new Flock();
        Quackable mallardDuck1 = duckFactory.createMallardDuck();
        Quackable mallardDuck2 = duckFactory.createMallardDuck();
        Quackable mallardDuck3 = duckFactory.createMallardDuck();
        Quackable mallardDuck4 = duckFactory.createMallardDuck();
        flockOfMallards.add(mallardDuck1);
        flockOfMallards.add(mallardDuck2);
        flockOfMallards.add(mallardDuck3);
        flockOfMallards.add(mallardDuck4);

        flockOfDucks.add(flockOfMallards);

        Quackologist quackologist = new Quackologist();
        flockOfDucks.registerObserver(quackologist);

        System.out.println("\nDuck Simulator: Whole Flock Simulation");
        simulate(flockOfDucks);

        System.out.println("\nDuck Simulator: Mallard Flock Simulation");
        simulate(flockOfMallards);
    }

    void simulate(Quackable duck) {
        duck.quack();
    }
}
