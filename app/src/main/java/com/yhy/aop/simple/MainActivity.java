package com.yhy.aop.simple;

import android.os.Bundle;

import com.yhy.aop.annotation.ExitSticker;

import androidx.appcompat.app.AppCompatActivity;

@ExitSticker
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
