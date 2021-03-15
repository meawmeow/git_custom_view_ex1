package cat.meaw.meow.mycustomsviewex1.progress.canvas

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import cat.meaw.meow.mycustomsviewex1.R

//reference link https://blog.usejournal.com/hidden-gem-of-android-custom-views-c116a61c4640

class ProgressCircleViewS3 @JvmOverloads constructor(context: Context,attrs:AttributeSet):View(context, attrs) {

    private var circleRadius= 170f
    private var viewCenterVertical = 0f
    private var viewCenterHorizontal = 0f

    private var scaleCircleAnimator : ValueAnimator
    private var tickAnimator: ValueAnimator
    private var arcAnimator: ValueAnimator

    private var drawTickMark = false
    private var startAngle = 0f
    private var sweepAngle = 0f

    private var left = 0f
    private var top = 0f
    private var right = 0f
    private var bottom = 0f
    private var pathDt = 0f

    private var startX = 0f
    private var startY = 0f

    private var pathLength = 0f
    lateinit var pathMeasure: PathMeasure
    private val position = FloatArray(2)

    private val paint = Paint()
    private var drawingPath = Path()
    private val linePath = Path()
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


        scaleCircleAnimator = ValueAnimator.ofFloat(150f, circleRadius)
        scaleCircleAnimator.duration = 2000
        scaleCircleAnimator.interpolator = AccelerateInterpolator()
        scaleCircleAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                circleRadius = animation.animatedValue as Float
                animation.repeatCount = ValueAnimator.INFINITE
                animation.repeatMode = ValueAnimator.REVERSE
                invalidate()
            }
        })

        tickAnimator = ValueAnimator.ofFloat(0f, 1f)
        tickAnimator.duration = ANIMATION_DURATION.toLong()
        tickAnimator.interpolator = FastOutSlowInInterpolator()
        tickAnimator.addUpdateListener { animation ->
            pathDt = animation.animatedValue as Float
            val distance: Float = pathDt * pathLength
            pathMeasure.getPosTan(distance, position, null)
            drawingPath.lineTo(position.get(0), position.get(1))
            invalidate()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("DSSE","onDetachedFromWindow")
        arcAnimator.cancel()
        scaleCircleAnimator.cancel()
        tickAnimator.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //viewHeight = (height / 2).toFloat()
        //viewWidth = (width / 2).toFloat()

        //canvas.drawCircle(viewWidth, viewHeight, circleRadius.toFloat(), paint)

        if (drawTickMark) {
            canvas.drawCircle(
                viewCenterHorizontal,
                viewCenterVertical,
                circleRadius.toFloat(),
                paint
            )
            canvas.drawPath(drawingPath, paint)
            //canvas.drawPath(linePath,paint)
            return
        }
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewCenterHorizontal = w.toFloat() / 2
        viewCenterVertical = h.toFloat() / 2
        initViewProperties()
        Log.d("DSHH", "onSizeChanged..")
    }

    fun initViewProperties(){
        left = viewCenterHorizontal - circleRadius
        top = viewCenterVertical - circleRadius
        right = viewCenterHorizontal + circleRadius
        bottom = viewCenterVertical + circleRadius
        rectF.set(left, top, right, bottom)

        startX = viewCenterHorizontal - 70
        startY = viewCenterVertical - 10
        var stopX = viewCenterHorizontal - 30
        var stopY = viewCenterVertical + 40
        var endX = viewCenterHorizontal + 60
        var endY = viewCenterVertical - 50
        linePath.moveTo(startX, startY)
        linePath.lineTo(stopX, stopY)
        linePath.lineTo(endX, endY)

        drawingPath = Path()
        drawingPath.moveTo(startX, startY)
        pathMeasure = PathMeasure(linePath, false)
        pathLength = pathMeasure.length

    }
    fun startAnimation(){
        scaleCircleAnimator.cancel()

        tickAnimator.cancel()
        drawingPath.reset()
        drawingPath.moveTo(startX, startY);

        drawTickMark = false
        arcAnimator.start()
        invalidate()
    }
    fun stopAnimation(){
        arcAnimator.cancel()
        scaleCircleAnimator.start()
        drawTickMark = true
        tickAnimator.start();
        invalidate()
    }
}