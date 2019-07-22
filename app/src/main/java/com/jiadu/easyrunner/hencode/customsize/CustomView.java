package com.jiadu.easyrunner.hencode.customsize;

import android.content.Context;
import android.graphics.Canvas;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View {

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(100, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
    @Override
    public void layout(int l, int t, int r, int b) {
        Log.e("TAG", l + "," + t + "," + r + "," + b);
        super.layout(l, t, 100, 100);
    }

//
//
    /*

     */
}
