package com.duchen.annotation;

public class ViewInjector {

    private static final String SUFFIX = "$$ViewInject";

    public static void injectView(Object activity) {
        ViewInject proxyActivity = findProxyObject(activity);
        proxyActivity.inject(activity, activity);
    }

    public static void injectView(Object object, Object view) {
        ViewInject proxy = findProxyObject(object);
        proxy.inject(object, view);
    }

    private static ViewInject findProxyObject(Object activity) {
        try {
            Class<?> clazz = activity.getClass();
            Class<?> injectorClazz = Class.forName(clazz.getName() + SUFFIX);
            return (ViewInject) injectorClazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("can not find %s , something when compiler.", activity.getClass().getSimpleName() + SUFFIX));
    }
}
