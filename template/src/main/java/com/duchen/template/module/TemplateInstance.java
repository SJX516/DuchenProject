package com.duchen.template.module;

public class TemplateInstance implements TemplateModule {

    private static TemplateInstance sInstance;

    private TemplateScope mScope;

    public static TemplateInstance getInstance() {
        if (sInstance == null) {
            sInstance = new TemplateInstance();
        }
        return sInstance;
    }

    private TemplateInstance() {
    }

    public void setScope(TemplateScope scope) {
        mScope = scope;
    }

    public TemplateScope getScope() {
        return mScope;
    }

    @Override
    public void doSomeThing() {

    }
}
