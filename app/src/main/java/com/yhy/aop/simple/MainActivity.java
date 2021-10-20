package com.yhy.aop.simple;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yhy.aop.annotation.ClickAntiShakeIgnored;
import com.yhy.aop.annotation.BackSnack;
import com.yhy.aop.utils.Utils;

@BackSnack(value = "有种再按一次试试", interval = 5000, resolver = BackSnackListener.class)
public class MainActivity extends AppCompatActivity {

    private TextView tvClick;
    private TextView tvClickIgnore;
    private TextView tvClickLambda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Class<? extends Activity> main = Utils.annotatedActivity(this, BackSnack.class);
        Log.i("MainBackPager", main.getCanonicalName());

        tvClick = findViewById(R.id.tv_click);
        tvClickIgnore = findViewById(R.id.tv_click_ignore);
        tvClickLambda = findViewById(R.id.tv_click_lambda);

        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("被单击了");
            }
        });

        tvClickIgnore.setOnClickListener(new View.OnClickListener() {
            @ClickAntiShakeIgnored
            @Override
            public void onClick(View view) {
                log("被忽略了" + MainActivity.this.getPackageName());
            }
        });

        tvClickLambda.setOnClickListener(v -> {
            log("拉姆达被点击了");
        });

//        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void log(String log) {
        Log.i("MainActivity", log);
    }
}
