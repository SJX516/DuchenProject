package com.duchen.design.proxy.protect;

import com.duchen.design.proxy.protect.handler.NonOwnerInvocationHandler;
import com.duchen.design.proxy.protect.handler.OwnerInvocationHandler;

import java.lang.reflect.Proxy;

public class PersonImpl implements Person {

    private String mName;
    private String mInterests;
    private int mRating = 0;
    private int mRatingCount = 0;

    public PersonImpl(String name, String interests) {
        mName = name;
        mInterests = interests;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getInterests() {
        return mInterests;
    }

    @Override
    public int getRating() {
        if (mRatingCount == 0) return 0;
        return mRating / mRatingCount;
    }

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public void setInterests(String interests) {
        mInterests = interests;
    }

    @Override
    public void addRating(int rating) {
        mRating += rating;
        mRatingCount++;
    }

    @Override
    public String toString() {
        return mName + "   " + mInterests + "   " + getRating();
    }

    public static Person getOwnerProxy(Person person) {
        return (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));
    }

    public static Person getNonOwnerProxy(Person person) {
        return (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
    }
}
