package com.jiadu.easyrunner.hencode.customsize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.jiadu.easyrunner.hencode.utils.Utils;
import com.socks.library.KLog;

public class CircleView extends View {

    private static final float RADIUS = Utils.Companion.dp2px(80);
    private static final float PADDING = Utils.Companion.dp2px(20);
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 去除super.onMeasure()
        // xml中设置的width为200、300， 如何获取设置的值进行修正呢？通过MeasureSpec
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        KLog.i(" width mode : " + widthMode + " , size = " + widthSize);
        KLog.i("height mode : " + heightMode + " , size = " + heightSize);
        int length = (int) ((RADIUS + PADDING) * 2);
        KLog.d(length);

        int destWidth = length;
        if (widthMode == MeasureSpec.EXACTLY) {
            KLog.e();
            destWidth = Math.max(widthSize, length);

        } else if (widthMode == MeasureSpec.AT_MOST) {

        }

        setMeasuredDimension(destWidth, length);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#CD0000"));
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, mPaint);
    }
}
