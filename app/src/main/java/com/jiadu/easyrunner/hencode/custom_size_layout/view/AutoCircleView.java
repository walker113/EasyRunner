package com.jiadu.easyrunner.hencode.custom_size_layout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.jiadu.easyrunner.hencode.DrawOne.Utils;

public class AutoCircleView extends View {


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.Companion.dp2px(80);
    private static final float PADDING = Utils.Companion.dp2px(30);


    public AutoCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = (int) ((RADIUS + PADDING) * 2);

        int resolveWidth = resolveSize(width, widthMeasureSpec);
        int resolveHeight = resolveSize(width, heightMeasureSpec);

        setMeasuredDimension(resolveWidth, resolveHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int x = (int) (RADIUS + PADDING);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(x, x, RADIUS, mPaint);

    }
}
