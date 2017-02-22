package com.test.dagger.Study;

/**
 * Created by netease on 17/1/5.
 */
public class AScope implements IAScope {

    @Override
    public void doANeed() {
        Application.applicationComponent.getBLogic().doBWork();
    }
}
