package com.test.Study;

import dagger.Component;

/**
 * Created by netease on 17/1/5.
 */
@Component(modules = AModule.class)
public interface AComponent {

    IAScope getAScope();

    void inject(ALogic aLogic);


}
