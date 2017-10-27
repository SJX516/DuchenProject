package com.duchen.template.usage.TestAnnotation;

public interface ViewInject<T> {
    void inject(T target, Object source);
}
