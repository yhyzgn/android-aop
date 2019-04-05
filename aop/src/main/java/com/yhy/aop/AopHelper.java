package com.yhy.aop;

import android.app.Application;

import com.yhy.aop.annotation.Aop;
import com.yhy.aop.listener.OnAopExitListener;
import com.yhy.aop.utils.AopUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 18:55
 * version: 1.0.0
 * desc   : Aop助手类
 */
public class AopHelper {
    private static volatile AopHelper instance;

    private Application mApp;
    private Logger mLogger;
    private boolean mEnabled;
    private String mPkg;
    private boolean mDebug;

    private List<OnAopExitListener> mExitListenerList;

    private AopHelper() {
        if (null != instance) {
            throw new UnsupportedOperationException("Can not instantiate singleton class.");
        }
    }

    public static AopHelper getInstance() {
        if (null == instance) {
            synchronized (AopHelper.class) {
                if (null == instance) {
                    instance = new AopHelper();
                }
            }
        }
        return instance;
    }

    public AopHelper init(Application application) {
        return init(application, true);
    }

    public AopHelper init(Application application, boolean debug) {
        mApp = application;
        mDebug = debug;
        config();
        return this;
    }

    public AopHelper logger(Logger logger) {
        mLogger = logger;
        return this;
    }

    public void log(String msg) {
        if (null != mLogger) {
            mLogger.log(msg);
        }
    }

    public Application getApp() {
        return mApp;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public String getPkg() {
        return mPkg;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public List<OnAopExitListener> getExitListenerList() {
        return mExitListenerList;
    }

    private void config() {
        Aop config = mApp.getClass().getAnnotation(Aop.class);
        // 如果未添加注解，则关闭Aop功能
        if ((mEnabled = null != config)) {
            mPkg = config.value();
        }

        // 子线程，执行一些耗时操作
        new Thread() {
            @Override
            public void run() {
                List<Class<? extends OnAopExitListener>> listeners = AopUtils.getImplementations(mApp, mPkg, OnAopExitListener.class);
                if (null != listeners && !listeners.isEmpty()) {
                    mExitListenerList = new ArrayList<>();
                    for (Class<? extends OnAopExitListener> listener : listeners) {
                        if (listener.isInterface() || Modifier.isAbstract(listener.getModifiers())) {
                            continue;
                        }
                        try {
                            mExitListenerList.add(listener.newInstance());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 日志打印器
     */
    public interface Logger {

        /**
         * 日志打印
         *
         * @param msg 日志信息
         */
        void log(String msg);
    }
}
