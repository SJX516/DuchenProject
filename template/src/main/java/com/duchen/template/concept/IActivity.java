package com.duchen.template.concept;

import android.content.Intent;

/**
 * 定义项目中通用的activity接口
 */
public interface IActivity {

    /**
     * 加载数据
     */
    void loadData();

    /**
     * 处理Intent
     */
    void handleIntent(Intent intent);

    /**
     * 准备MVP模型中的P，Fragment通过P去执行加载数据的动作，
     * 要求在handleIntent()之后，onCreate()之前调用
     */
    void prepareLogic();

    /**
     * finish时，是否拉起首页
     */
    boolean launchMainActivityOnFinish();
}
