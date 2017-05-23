package com.duchen.design.proxy.protect;

public interface Person {
    String getName();
    String getInterests();
    int getRating();

    void setName(String name);
    void setInterests(String interests);
    void addRating(int rating);
}
