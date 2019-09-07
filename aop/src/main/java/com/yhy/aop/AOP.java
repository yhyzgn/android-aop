package com.yhy.aop;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 18:14
 * version: 1.0.0
 * desc   :
 */
public class AOP {

    private static volatile AOP aop;

    private Application app;
    private boolean debug;
    private Logger logger;

    private AOP(Application app) {
        if (null != aop) {
            throw new UnsupportedOperationException("Singleton class can not be instantiate.");
        }
        this.app = app;
    }

    public static Config init(@NonNull Application app) {
        return new Config(app);
    }

    @NonNull
    public static AOP aop() {
        validate();
        return aop;
    }

    @NonNull
    public static Application app() {
        validate();
        return aop.app;
    }

    public static boolean debug() {
        validate();
        return aop.debug;
    }

    @NonNull
    public static Logger logger() {
        validate();
        return aop.logger;
    }

    private static void validate() {
        if (null == aop || null == aop.app) {
            throw new IllegalStateException("You must init AOP with method 'AOP.init(Application app)' at first, and the argument 'app' can not be null.");
        }
    }

    public static class Config {

        private Config(Application app) {
            aop = new AOP(app);
            // 默认logger
            aop.logger = new LoggerAdapter();
        }

        public Config debug(boolean debug) {
            aop.debug = debug;
            return this;
        }

        public Config logger(Logger logger) {
            aop.logger = logger;
            return this;
        }
    }

    public interface Logger {

        void log(String log);
    }

    private static class LoggerAdapter implements Logger {
        @Override
        public void log(String log) {
            validate();
            if (debug()) {
                Log.i("AOP", log);
            }
        }
    }
}
