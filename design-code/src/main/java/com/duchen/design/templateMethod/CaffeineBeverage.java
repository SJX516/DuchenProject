package com.duchen.design.templateMethod;

public abstract class CaffeineBeverage {

    /**
     * 这就是模板方法,被声明为final,以免子类改变这个算法的顺序
     */
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    protected abstract void brew();

    protected boolean customerWantsCondiments() {
        return true;
    }

    protected abstract void addCondiments();

    void boilWater() {
        System.out.println("Boiling water");
    }

    void pourInCup() {
        System.out.println("Pouring into cup");
    }
}
