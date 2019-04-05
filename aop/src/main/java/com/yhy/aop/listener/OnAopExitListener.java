package com.yhy.aop.listener;

import android.content.Context;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 17:11
 * version: 1.0.0
 * desc   : Aop退出事件
 */
public interface OnAopExitListener {

    /**
     * 粘性退出事件回调
     *
     * @param ctx 上下文对象
     * @param msg 提示文字
     */
    void onAopExit(Context ctx, String msg);
}
