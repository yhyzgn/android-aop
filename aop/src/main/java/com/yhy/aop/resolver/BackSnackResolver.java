package com.yhy.aop.resolver;

import android.content.Context;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-08 1:02
 * version: 1.0.0
 * desc   : 防退出提示回调
 */
@FunctionalInterface
public interface BackSnackResolver {

    /**
     * 回调方法
     *
     * @param context 上下文
     * @param message 提示消息
     */
    void onBack(Context context, String message);
}
