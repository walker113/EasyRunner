package com.jiadu.easyrunner.hencode.utils

import android.content.res.Resources
import android.util.TypedValue

public class Utils {

    companion object {
        fun dp2px(dp: Float) : Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
        }

    }

}