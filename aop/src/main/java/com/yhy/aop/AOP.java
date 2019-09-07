package com.yhy.aop;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yhy.aop.annotation.MainBackResolver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 18:14
 * version: 1.0.0
 * desc   : AOP配置信息
 */
public class AOP {

    private static volatile AOP aop;

    private Application app;
    private Class<? extends Activity> main;
    private boolean debug;
    private Logger logger;

    /**
     * 构造方法
     *
     * @param app 当前Application
     */
    private AOP(Application app) {
        if (null != aop) {
            throw new UnsupportedOperationException("Singleton class can not be instantiate.");
        }
        this.app = app;
    }

    /**
     * 初始化
     *
     * @param app 当前Application
     * @return 配置对象
     */
    public static Config init(@NonNull Application app) {
        return new Config(app);
    }

    /**
     * 当前AOP对象
     *
     * @return AOP对象
     */
    @NonNull
    public static AOP aop() {
        validate();
        return aop;
    }

    /**
     * 当前Application对象
     *
     * @return Application
     */
    @NonNull
    public static Application app() {
        validate();
        return aop.app;
    }

    /**
     * 主页面Activity类
     *
     * @return 主页面
     */
    @Nullable
    public static Class<? extends Activity> main() {
        validate();
        return aop.main;
    }

    /**
     * 是否调试模式
     *
     * @return 是否调试
     */
    public static boolean debug() {
        validate();
        return aop.debug;
    }

    /**
     * 日志打印器
     *
     * @return 日志打印器
     */
    @NonNull
    public static Logger logger() {
        validate();
        return aop.logger;
    }

    /**
     * 检查一些东西
     */
    private static void validate() {
        if (null == aop || null == aop.app) {
            throw new IllegalStateException("You must init AOP with method 'AOP.init(Application app)' at first, and the argument 'app' can not be null.");
        }
    }

    /**
     * 配置器
     */
    public static class Config {

        /**
         * 构造函数
         *
         * @param app Application
         */
        private Config(Application app) {
            aop = new AOP(app);
            // 默认logger，动态代理获取
            aop.logger = proxyLogger();
        }

        /**
         * 主页面类，用于防退出处理
         *
         * @param main 主页面类
         * @return 当前配置对象
         */
        public Config main(Class<? extends Activity> main) {
            // 主页面类中  必须重写onBackPressed()方法
            try {
                main.getDeclaredMethod("onBackPressed");
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Your Activity '" + main.getName() + "' must override the method 'onBackPressed().'");
            }
            // 是否被 @MainBackResolver 注解，因为需要从注解中获取相关信息
            MainBackResolver resolver = main.getAnnotation(MainBackResolver.class);
            if (null == resolver) {
                throw new IllegalStateException("Must annotate class '" + main.getName() + "' with @MainBackResolver.");
            }
            aop.main = main;
            return this;
        }

        /**
         * 是否调试模式
         *
         * @param debug 是否
         * @return 当前配置对象
         */
        public Config debug(boolean debug) {
            aop.debug = debug;
            return this;
        }

        /**
         * 日志打印器
         *
         * @param logger 日志打印器
         * @return 当前配置对象
         */
        public Config logger(Logger logger) {
            aop.logger = logger;
            return this;
        }

        /**
         * 动态代理获取默认日志打印器
         *
         * @return 默认日志打印器
         */
        private Logger proxyLogger() {
            return (Logger) Proxy.newProxyInstance(Logger.class.getClassLoader(), new Class<?>[]{Logger.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) {
                    if (null != args && args.length > 0 && args[0] instanceof String) {
                        validate();
                        if (aop.debug) {
                            Log.i("AOP", (String) args[0]);
                        }
                    }
                    return null;
                }
            });
        }
    }

    /**
     * 日志打印器
     */
    public interface Logger {

        /**
         * 打印日志
         *
         * @param log 日志内容
         */
        void log(String log);
    }
}
