package com.yhy.aop.simple.aop;

import android.content.Context;
import android.widget.Toast;

import com.yhy.powerexam.aop.listener.OnAopExitListener;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 19:26
 * version: 1.0.0
 * desc   :
 */
public class OnAopExitCallback implements OnAopExitListener {

    @Override
    public void onAopExit(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
