package com.duchen.annotation.processer;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ViewProcessor extends AbstractProcessor {

    private Messager messager;

    private Elements elementsUtils;

    private Map<String, ProxyInfo> mProxyMap;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
