package cat.meaw.meow.mycustomsviewex1.progress.canvas

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import cat.meaw.meow.mycustomsviewex1.R

class ProgressCircleViewS1 @JvmOverloads constructor(context: Context,attrs:AttributeSet):View(context, attrs) {
    private var circleRadius= 170f
    private var viewCenterVertical = 0f
    private var viewCenterHorizontal = 0f

    private var arcAnimator: ValueAnimator

    private var startAngle = 0f
    private var sweepAngle = 0f

    private var left = 0f
    private var top = 0f
    private var right = 0f
    private var bottom = 0f


    private val paint = Paint()
    private val paintCircle = Paint()
    private val rectF = RectF()

    private val ANIMATION_DURATION = 1500
    private val PROPERTY_START_ANGLE = "startAngle"
    private val PROPERTY_SWEEP_ANGLE = "sweepAngle"
    var propertyStartAngle = PropertyValuesHolder.ofFloat(PROPERTY_START_ANGLE, 0f, 360f)
    var propertySweepAngle = PropertyValuesHolder.ofFloat(PROPERTY_SWEEP_ANGLE, 0f, 360f)

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ProgressBarsViewS1, 0, 0
        )
        paint.color = typedArray.getColor(R.styleable.ProgressBarsViewS1_color, 0)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = typedArray.getFloat(R.styleable.ProgressBarsViewS1_widthStroke, 10f)

        paintCircle.color = Color.GRAY
        paintCircle.style = Paint.Style.STROKE
        paintCircle.strokeWidth = typedArray.getFloat(R.styleable.ProgressBarsViewS1_widthStroke, 10f)
        typedArray.recycle()

        arcAnimator = ValueAnimator()
        arcAnimator.setValues(propertyStartAngle, propertySweepAngle)
        arcAnimator.duration = ANIMATION_DURATION.toLong()
        arcAnimator.interpolator = LinearInterpolator()
        arcAnimator.addUpdateListener { animation ->
            startAngle = animation.getAnimatedValue(PROPERTY_START_ANGLE) as Float
            sweepAngle = animation.getAnimatedValue(PROPERTY_SWEEP_ANGLE) as Float
            //Log.d("DSHH","startAngle = $startAngle sweepAngle = $sweepAngle")
            animation.repeatCount = ValueAnimator.INFINITE
            animation.repeatMode = ValueAnimator.REVERSE
            invalidate()
        }
        arcAnimator.start()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(viewCenterHorizontal,viewCenterVertical,circleRadius,paintCircle)
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("DSSE","onDetachedFromWindow")
        arcAnimator.cancel()
        //scaleCircleAnimator.cancel()
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewCenterHorizontal = w.toFloat() / 2
        viewCenterVertical = h.toFloat() / 2
        initViewProperties()
    }

    fun initViewProperties(){
        left = viewCenterHorizontal - circleRadius;
        top = viewCenterVertical - circleRadius;
        right = viewCenterHorizontal + circleRadius;
        bottom = viewCenterVertical + circleRadius;
        rectF.set(left, top, right, bottom);
    }
}