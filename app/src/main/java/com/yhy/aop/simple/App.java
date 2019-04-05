package com.yhy.aop.simple;

import android.app.Application;
import android.util.Log;

import com.yhy.aop.AopHelper;
import com.yhy.aop.annotation.Aop;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 19:23
 * version: 1.0.0
 * desc   :
 */
@Aop("com.yhy.aop.simple.aop")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AopHelper.getInstance().init(this).logger(new AopHelper.Logger() {
            @Override
            public void log(String msg) {
                Log.i("App", msg);
            }
        });
    }
}
