package com.test.dagger.Study;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netease on 17/1/5.
 */
@Module
public class ApplicationModule {

    private AComponent mAComponent;

    public ApplicationModule(AComponent AComponent) {
        mAComponent = AComponent;
    }

    @Provides
    ALogic provideAModule() {
        return new ALogic(mAComponent);
    }

    @Provides IBScope provideBScope() {
        return new BScope();
    }
}
