package com.duchen.annotation;

public interface ViewInject<T> {
    void inject(T target, Object source);
}
