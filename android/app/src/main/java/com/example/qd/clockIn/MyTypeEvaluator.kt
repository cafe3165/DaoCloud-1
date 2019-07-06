package com.example.qd.clockIn

import android.animation.TypeEvaluator
import android.graphics.Point
import android.util.Log

class MyTypeEvaluator :TypeEvaluator<Point>{
    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        var point = Point()
        point.x = ( (endValue!!.x-startValue!!.x)*fraction ).toInt()
        point.y = ( (endValue!!.y-startValue!!.y)*fraction ).toInt()
        return point
    }
}