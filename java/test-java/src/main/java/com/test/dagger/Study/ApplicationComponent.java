package com.test.dagger.Study;

import dagger.Component;

/**
 * Created by netease on 17/1/5.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    ALogic getALogic();

    BLogic getBLogic();

}
