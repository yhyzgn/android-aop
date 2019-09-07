package com.yhy.aop.simple;

import android.content.Context;
import android.widget.Toast;

import com.yhy.aop.callback.OnBackCallback;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-08 2:44
 * version: 1.0.0
 * desc   :
 */
public class OnBackListener implements OnBackCallback {

    @Override
    public void callback(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
