package com.test.Study;

import javax.inject.Inject;

/**
 * Created by netease on 17/1/5.
 */
public class ALogic {

    AComponent mAComponent;

    @Inject IAScope scope;

    public ALogic(AComponent component) {
        mAComponent = component;
        mAComponent.inject(this);
        scope.doANeed();
    }

    public void doAWork() {

    }
}
