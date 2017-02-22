package com.test.dagger.base;

import javax.inject.Inject;

/**
 * Created by hzshangjiaxiong on 17/1/4.
 */
public class TestData {

    String data = "";

    @Inject public TestData() {
        data = "TestData init";
    }

    public String getData() {
        return data;
    }
}
