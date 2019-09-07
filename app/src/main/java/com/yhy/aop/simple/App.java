package com.yhy.aop.simple;

import android.app.Application;

import com.yhy.aop.AOP;
import com.yhy.aop.annotation.EnableClickResolver;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 19:23
 * version: 1.0.0
 * desc   :
 */
@EnableClickResolver(3000)
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AOP.init(this).main(MainActivity.class).debug(true);
    }
}
