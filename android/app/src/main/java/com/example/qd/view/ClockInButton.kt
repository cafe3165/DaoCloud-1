package com.example.qd.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.example.qd.R

class ClockInButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var paint:Paint = Paint()
    var myWidth:Int = 0;
    var myHeight:Int = 0;
    var radius:Int = 0
    var waveRadius = 0
    var myColor:Int = 0
    var wavePaint = Paint()
    var alpha  = 0
    var touchX :Float= 0f
    var touchY :Float= 0f
    init {

        attrs?.let{
            val arrayType = context.obtainStyledAttributes(attrs, R.styleable.circleBt,defStyleAttr,0)
            myColor = arrayType.getColor(R.styleable.circleBt_circleColor,Color.BLUE)
            arrayType.recycle()
        }
        paint.color = myColor
        paint.isAntiAlias = true

        wavePaint.color = Color.WHITE
        wavePaint.alpha = 80
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        myWidth = MeasureSpec.getSize(widthMeasureSpec)
        myHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        radius = reckonRadius(myHeight,myWidth)


        canvas?.drawCircle(myWidth/2f,myHeight/2f,radius.toFloat(),paint)
//        wavePaint.alpha = alpha
        canvas?.drawCircle(touchX,touchY,waveRadius.toFloat(),wavePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event!!.x
        touchY = event!!.y
        return super.onTouchEvent(event)
    }

    var reckonRadius = { myHeight:Int, myWidth:Int->if(myHeight>myWidth)
        myWidth/2
        else myHeight/2
    }
}