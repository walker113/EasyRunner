package com.jiadu.easyrunner.hencode.customsize;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.socks.library.KLog;

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // [1:01:30]
        // getWidth()、getMeasuredWidth() 区别？
        // layout方法被调用之前，onMeasure方法调用之后，只能拿到MeasuredWidth、MeasureHeight；
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.max(width, height);
        // 保存测量的尺寸
        setMeasuredDimension(size, size);
    }


    @Override
    public void layout(int l, int t, int r, int b) {

        KLog.e();
        super.layout(l, t, r, b);
    }
}
