package com.test.dagger.Study;

/**
 * Created by netease on 17/1/5.
 */
public class Application {

    public static ApplicationComponent applicationComponent;
    public static AComponent aComponent;

    public static void main(String[] args) {

        aComponent = DaggerAComponent.builder().aModule(new AModule(provideAScope())).build();

        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(aComponent)).build();

    }

    static AScope provideAScope() {
        return new AScope();
    }

}
