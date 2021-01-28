package com.example.animationapp

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.getSize

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var paint = Paint().apply {
        color = 0xFFFF00e16.toInt()
    }
    private var paint1 = Paint().apply {
        color = 0xFFFFFFe16.toInt()
    }
    private var transl : Float = 2f
    private var ox : Float = 0f
    private var oy : Float = 0f
    private var ox1 : Float = 0f
    private var oy1 : Float = 0f
    private var speed : Float = 5f
    private var r : Float = 75f
    private var xd : Int = 1
    private var yd : Int = 1
    private var infinite : Boolean = true
    private var animationDuration : Float = 0f
    private var startAnimationTime : Long = System.currentTimeMillis()
    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.DrawView, defStyleAttr, defStyleRes
        )
        try {
            r = a.getDimension(R.styleable.DrawView_radius, 75f)
            transl =
                a.getFloat(R.styleable.DrawView_transl, 2f)
            speed = a.getFloat(R.styleable.DrawView_speed, 5f)
            infinite = a.getBoolean(R.styleable.DrawView_infinite, true)
            animationDuration = a.getFloat(R.styleable.DrawView_animationDuration, 0f)
        } finally {
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (infinite || System.currentTimeMillis() - startAnimationTime <= animationDuration) {
            if (xd == 1 && ox + speed + r > measuredWidth.toFloat()) {
                xd = -1
            } else {
                if (xd == -1 && ox - speed < r) {
                    xd = 1
                } else {
                    ox += speed * xd
                    ox1 -= speed * xd
                    transl += 1f * xd
                }
            }
        }
        val save = canvas?.save()
        canvas?.translate(0f, transl)
        canvas?.drawCircle(ox, oy, r, paint)
        canvas?.drawCircle(ox1, oy1, r, paint1)
        invalidate()
        if (save != null) {
            canvas.restoreToCount(save)
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec))
        ox = measuredWidth.toFloat()/2
        oy = measuredHeight.toFloat()/2
        ox1 = measuredWidth.toFloat()/2
        oy1 = measuredHeight.toFloat()/2
    }
}
