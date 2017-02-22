package com.test.thinkinjava.typeinfo.src.SimpleDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 51619 on 2016/3/31 0031.
 */

interface Interface {
    void doSomething();
    void someThingElse(String arg);
}

class RealObject implements Interface{

    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void someThingElse(String arg) {
        System.out.println("somethingElse " + arg);
    }
}

class DynamicProxyHandler implements InvocationHandler {

    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        直接在这里输出 proxy 会报错
//        System.out.print(proxy);
//        Exception in thread "main" java.lang.StackOverflowError
//        at SimpleDynamicProxy.DynamicProxyHandler.invoke(SimpleDynamicProxy.java:39)
//        at SimpleDynamicProxy.$Proxy0.toString(Unknown Source)

//        proxy object 是代理对象，proxied object 是被代理对象。
//
//        1. 尽管你在产生 Proxy class/instance 时有指明 Proxy class 要 implements 哪个(些)
//        interface，但 proxy object 的代理机制是全面性的(不仅限于代理 interface 所明列的操作)。
//        也就是说对 proxy object 进行任何操作都会委托给 InvocationHandler 的 invoke method。
//
//        2. Invocation handler 的 invoke method 运行里的
//        System.out.println("proxy: " + proxy);
//
//        大致上可以看成等效于
//        System.out.println("proxy: " + proxy.toString());
//
//        1+2 的综合就是：
//        运行 proxy object 的任一个 method 都会委托给 DynamicProxyHandler object 的 invoke method，
//        这个 method 做的事包括运行 proxy object 的 toString method，那么...（接句首)。

        System.out.println("**** proxy: " + proxy.getClass() +
                " , method: "+method + " , args: " + args);
        if( args != null ) {
            for( Object arg : args ) {
                System.out.println("  " + arg);
            }
        }
        return method.invoke(proxied , args);
    }
}

public class SimpleDynamicProxy {
    public static void consumer(Interface iface){
        iface.doSomething();
        iface.someThingElse("bonobo");
    }

    public static void main(String[] args){
        RealObject realObject = new RealObject();
        consumer(realObject);
        Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader() ,
                new Class[]{Interface.class}, new DynamicProxyHandler(realObject));
        consumer(proxy);
    }
}
