package com.jiadu.easyrunner.hencode.custom_size_layout.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

public class TabLayout extends ViewGroup {
    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 保存子View的位置信息
     */
    List<Rect> childBounds = new ArrayList<>();

    /**
     * 将计算过程放在onMeasure中，然后再layout中保存
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthUsed = 0;
        int heightUsed = 0;

        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);

        // 这一行最高的View的Height
        int lineMaxHeight = 0;
        // 一行的使用宽度
        int lineUsedWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            // android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
            // margin需要特殊的ViewGroup$MarginLayoutParams才能获取,需要需要重写 generateLayoutParams
            // 以上流程通过 measureChildWithMargins 方法解决

            // 一、widthUsed设置为0
            measureChildWithMargins(child,
                    widthMeasureSpec, 0,
                    heightMeasureSpec, heightUsed);
            // 二、计算换行处理
            if (specMode != MeasureSpec.UNSPECIFIED &&
                    (lineUsedWidth + child.getMeasuredWidth()) > specWidth) {

                lineUsedWidth = 0;
                // 换行 需要更新 高度
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(child,
                        widthMeasureSpec, 0,
                        heightMeasureSpec, heightUsed);

            }


            // 2. 测量之后可以拿到child view的实际位置,然后保存到rect中
            Rect childBound ;
            if (childBounds.size() <= i) {
                childBound = new Rect();
                childBounds.add(childBound);
            } else {
                childBound = childBounds.get(i);
            }
            // 2.1. 位置跟Layout的计算方式有关；[2:01:15]
            childBound.set(
                    lineUsedWidth,
                    heightUsed,
                    // + 子View的宽度、高度
                    lineUsedWidth + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());

            // 2.2 更新 usedWidth、usedHeight
            lineUsedWidth += child.getMeasuredWidth();
//            heightUsed += child.getMeasuredHeight();

            widthUsed = Math.max(widthUsed, lineUsedWidth);
            // 保留最高的高度；
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        // 3. 需要计算自身尺寸
        int width = widthUsed;
        // 3.1 注意高度应该是 所有子View 中最高的那个
        int height = lineMaxHeight + heightUsed;
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 遍历子View，并将尺寸和位置赋值子View
        // 指派子View的位置和尺寸；
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childBounds.get(i);

            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }

    }

    // margin需要特殊的ViewGroup$MarginLayoutParams才能获取,需要需要重写 generateLayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attr) {
        return new MarginLayoutParams(getContext(), attr);
    }
}
