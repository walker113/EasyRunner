package com.jiadu.easyrunner.hencode.DrawOne.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jiadu.easyrunner.hencode.utils.Utils

/**
 *  顺时针或逆时针画的？复杂图形时，相交部分的处理
 */
class DashBoard : View {

    val ANGLE = 120f
    val RADIUS = Utils.dp2px(150f)
    val LENGTH = Utils.dp2px(100f)
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var pathMeasure: PathMeasure? = null
    val dashPath = Path()
    var effect: PathDashPathEffect? = null

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {

    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr) {

    }


    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2f)

        val dashPathWidth = Utils.dp2px(2f)
        dashPath.addRect(0f, 0f, dashPathWidth, Utils.dp2px(10f), Path.Direction.CW)

        val arcPath = Path()
        arcPath.addArc(
            (width / 2 - RADIUS),
            (height / 2 - RADIUS),
            (width / 2 + RADIUS),
            (height / 2 + RADIUS),
            90 + ANGLE / 2,
            360 - ANGLE
        )

        pathMeasure = PathMeasure(arcPath, false)
        val advance = (pathMeasure!!.length - dashPathWidth) / 20
        effect = PathDashPathEffect(dashPath, advance, 0f, PathDashPathEffect.Style.ROTATE)

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        // 画线
        canvas?.drawArc(
            (width / 2 - RADIUS),
            (height / 2 - RADIUS),
            (width / 2 + RADIUS),
            (height / 2 + RADIUS),
            90 + ANGLE / 2,
            360 - ANGLE,
            false, paint
        )

        // x = cos
        // y = sin
        val currentAngle = getAngleFromMark(1)
        canvas?.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            Math.cos(Math.toRadians(currentAngle* LENGTH)).toFloat(),
            Math.sin(Math.toRadians(currentAngle* LENGTH)).toFloat(), paint
        )

        // 画刻度
        paint.pathEffect = effect
        canvas?.drawArc(
            (width / 2 - RADIUS),
            (height / 2 - RADIUS),
            (width / 2 + RADIUS),
            (height / 2 + RADIUS),
            90 + ANGLE / 2,
            360 - ANGLE,
            false, paint
        )
        // 可以入联系y7kp了，或者惠普的i5 + 2060 也行，其实两部机器都是可以的，就是没有我想要的配置，联想是显卡
        // 惠普是 cpu和 内存




        paint.pathEffect = null

    }

    private fun getAngleFromMark(mark: Int): Double {

        return ((90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark).toDouble())

    }


}