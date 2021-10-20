package com.yhy.aop.simple;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yhy.aop.resolver.BackSnackResolver;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-08 2:44
 * version: 1.0.0
 * desc   :
 */
public class BackSnackListener implements BackSnackResolver {

    @Override
    public void onBack(Context context, String message) {
        Log.i("BackSnackListener", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
