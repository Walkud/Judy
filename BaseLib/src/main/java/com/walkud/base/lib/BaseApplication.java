package com.walkud.base.lib;

import android.app.Application;

import com.walkud.base.lib.utils.AppContextUtil;

/**
 * Created by Zhuliya on 2018/5/21
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContextUtil.init(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
