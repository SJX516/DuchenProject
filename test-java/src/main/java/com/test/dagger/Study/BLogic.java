package com.test.dagger.Study;

import javax.inject.Inject;

/**
 * Created by netease on 17/1/5.
 */
public class BLogic {

    @Inject
    public BLogic(IBScope scope) {
        scope.doBNeed();
    }

    public void doBWork() {

    }
}
