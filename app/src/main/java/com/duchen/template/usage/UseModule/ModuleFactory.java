package com.duchen.template.usage.UseModule;

import com.duchen.template.concept.IModule;
import com.duchen.template.module.TemplateModule;

import java.util.HashMap;

public class ModuleFactory {

    public enum ModuleType {
        TEMPLATE
    }

    private static ModuleFactory sInstance = new ModuleFactory();
    private HashMap<ModuleType, IModule> mModules = new HashMap<>();

    private ModuleFactory() {}

    public static ModuleFactory getInstance() {
        return sInstance;
    }

    public TemplateModule getTemplateModule() {
        return getModule(ModuleType.TEMPLATE);
    }

    public void registerModule(ModuleType type, IModule module) {
        mModules.put(type, module);
    }

    private <T extends IModule> T getModule(ModuleType type) {
        return (T) mModules.get(type);
    }
}
