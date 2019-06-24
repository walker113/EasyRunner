package com.jiadu.easyrunner.hencode.DrawOne.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jiadu.easyrunner.hencode.DrawOne.Utils

/**
 *  顺时针或逆时针画的？复杂图形时，相交部分的处理
 */
class DashBoard : View {

    val ANGLE = 120f
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var pathMeasure: PathMeasure? = null
    val dashPath = Path()

    constructor(context: Context) : super(context) {


    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {

    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr) {

    }


    init {

//        dashPath.addRect()

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2f)
//        paint.pathEffect = PathDashPathEffect()

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawArc(
            (width / 2 - 200).toFloat(),
            (height / 2 - 200).toFloat(),
            (width / 2 + 200).toFloat(),
            (height / 2 + 200).toFloat(),
            90 + ANGLE / 2,
            360 - ANGLE,
            false, paint
        )
    }


}