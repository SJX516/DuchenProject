package com.test.dagger.Study;

/**
 * Created by netease on 17/1/5.
 */
public class BScope implements IBScope {

    @Override
    public void doBNeed() {
        Application.applicationComponent.getALogic().doAWork();
    }

}
