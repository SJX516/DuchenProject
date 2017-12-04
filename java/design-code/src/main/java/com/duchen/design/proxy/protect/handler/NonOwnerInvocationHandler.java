package com.duchen.design.proxy.protect.handler;

import com.duchen.design.proxy.protect.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class NonOwnerInvocationHandler implements InvocationHandler {

    Person mPerson;

    public NonOwnerInvocationHandler(Person person) {
        mPerson = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("set")) {
            throw new IllegalAccessException();
        } else {
            return method.invoke(mPerson, args);
        }
    }
}
