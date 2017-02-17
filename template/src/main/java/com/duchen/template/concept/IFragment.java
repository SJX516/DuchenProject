package com.duchen.template.concept;

import android.os.Bundle;

public interface IFragment {

    /**
     * 解析setArgument()数据
     */
    void handleArguments(Bundle bundle);

    /**
     * 准备MVP模型中的P，Fragment通过P去执行加载数据的动作，
     * 要求在handleIntent()之后，onCreate()之前调用
     */
    void prepareLogic();
}
