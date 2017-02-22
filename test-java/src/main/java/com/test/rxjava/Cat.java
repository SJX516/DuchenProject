package com.test.rxjava;

import com.sun.jndi.toolkit.url.Uri;

import java.lang.Integer;
import java.util.Collections;
import java.util.List;

public class Cat implements Comparable<Cat>{

    Object image;
    int cuteness;

    @Override
    public int compareTo(Cat another) {
        return Integer.valueOf(cuteness).compareTo(another.cuteness);
    }
}

interface Api{
    List<Cat> queryCats(String query);
    Uri store(Cat cat);
}

class CatsHelper{

    Api api;

    public Uri saveTheCutestCat(String query){
        List<Cat> cats = api.queryCats(query);
        Cat cutestCat = Collections.max(cats);
        Uri savedUri = api.store(cutestCat);
        return savedUri;
    }
}