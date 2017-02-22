package com.test.dagger.Study;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netease on 17/1/5.
 */
@Module
public class AModule {

    private IAScope mIAScope;

    public AModule(IAScope IAScope) {
        mIAScope = IAScope;
    }

    @Provides IAScope provideScope() {
        return mIAScope;
    }
}
