package com.yhy.aop.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yhy.aop.annotation.AopExit;

@AopExit
public class MainActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.tv_test);

        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "哈哈", Toast.LENGTH_SHORT).show();
            }
        });

//        tvTest.setOnClickListener(v -> {
//            Toast.makeText(this, "哈哈", Toast.LENGTH_SHORT).show();
//        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}
