package com.test.thinkinjava.typeinfo.src.SimpleDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 51619 on 2016/3/31 0031.
 */

class MethodSelector implements InvocationHandler {

    private Object proxied;

    public MethodSelector(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("happy")) {
            System.out.println("proxy detected happy method invoke !! ");
        }
        return method.invoke(proxied, args);
    }
}

interface SomeMethods {
    void boring1();
    void boring2();
    void happy();
}

class DoMethods implements SomeMethods {

    @Override
    public void boring1() {
        System.out.println("doing boring1");
    }

    @Override
    public void boring2() {
        System.out.println("doing boring2");
    }

    @Override
    public void happy() {
        System.out.println("doing happy");
    }
}

public class SelectingMethods {

    public static void main(String[] args) throws Throwable {

        SomeMethods methods = (SomeMethods) Proxy.newProxyInstance(SomeMethods.class.getClassLoader(), new Class[]{SomeMethods.class},
                new MethodSelector(new DoMethods()));
        methods.boring1();
        methods.boring2();
        methods.happy();
//        Proxy.getInvocationHandler(methods).invoke(methods , methods.getClass().getMethods()[3] , new Object[]{});
    }

}
