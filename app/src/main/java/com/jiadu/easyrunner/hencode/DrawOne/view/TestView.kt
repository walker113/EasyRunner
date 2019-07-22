package com.jiadu.easyrunner.hencode.DrawOne.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *  顺时针或逆时针画的？复杂图形时，相交部分的处理
 */
class TestView : View {

    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()
    var pathMeasure: PathMeasure? = null

    constructor(context: Context) : super(context) {


    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {

    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr) {

    }

    /**
     * layout过程结束之后，实际尺寸改变了，onSizeChanged会别调用
     * 所以在onSizeChanged中设置path有什么好处呢？
     *      1. 每次图形尺寸变了，path都会被重置，保证绘制正常；
     *      2. 不会被过多的调用；比如View测量了多次，如果写在onMeasure里面，path会被调用多次；
     *
     *
     *
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.reset()
        // 先画一个Rect
//        path.addRect(
//            (width / 2 - 150).toFloat(),
//            (height / 2 - 300).toFloat(),
//            (width / 2 + 150).toFloat(),
//            (height / 2).toFloat(),
//            Path.Direction.CCW
//        )

        path.addCircle((width / 2).toFloat(), (height / 2).toFloat(), 20f, Path.Direction.CCW)
//        path.addCircle((width / 2).toFloat(), (height / 2).toFloat(), 400f, Path.Direction.CCW)
        // 如果逆时针画一个正方形
        // 然后逆时针画一个圆

        pathMeasure = PathMeasure(path, false)
        Log.e("TAG", (pathMeasure!!.length / 2 / Math.PI).toString())

    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        path.fillType = Path.FillType.EVEN_ODD
        canvas?.drawPath(path, paint)

    }


}