package com.duchen.design.singleton;

public class Singleton {

    private volatile static Singleton uniqueInstance;

    private Singleton() { }

    //普通版本,在单线程环境下没有问题
    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

    //直接同步方法,会造成执行效率下降
    public static synchronized Singleton getInstance2() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

    //双重检查,只在第一次执行同步,能在多线程保持良好的执行效率
    public static Singleton getInstance3() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
