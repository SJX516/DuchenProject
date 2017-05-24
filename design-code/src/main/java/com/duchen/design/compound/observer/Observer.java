package com.duchen.design.compound.observer;

import com.duchen.design.compound.Quackable;

public interface Observer {
    void update(Quackable duck);
}
