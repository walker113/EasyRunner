package com.jiadu.easyrunner.hencode.touch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.jiadu.easyrunner.R;
import com.socks.library.KLog;

public class TouchActivity extends AppCompatActivity {

    private TouchView touchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_touch);
        touchView = findViewById(R.id.touch_view);

        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KLog.e("onclick");

            }
        });

        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e("tooltiptext");
            }
        });
    }
}
