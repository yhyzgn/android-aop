package com.yhy.aop;

import android.app.Application;

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
        return this;
    }

    public AopHelper logger(Logger logger) {
        mLogger = logger;
        return this;
    }

    public void log(String msg, Logger.Level level) {
        if (null != mLogger) {
            mLogger.log(msg, level);
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

    /**
     * 日志打印器
     */
    public interface Logger {

        /**
         * 日志打印
         *
         * @param msg 日志信息
         */
        void log(String msg, Level level);

        enum Level {
            INFO, DEBUG, WARN, ERROR
        }
    }
}
