package com.duchen.template.module.impl;

import com.duchen.template.module.TemplateInstance;
import com.duchen.template.module.TemplateModule;
import com.duchen.template.module.TemplateScope;

public class TemplateModuleImpl implements TemplateModule {

    public TemplateModuleImpl(TemplateScope scope) {
        TemplateInstance.getInstance().setScope(scope);
    }

    @Override
    public void doSomeThing() {
        TemplateInstance.getInstance().doSomeThing();
    }
}
