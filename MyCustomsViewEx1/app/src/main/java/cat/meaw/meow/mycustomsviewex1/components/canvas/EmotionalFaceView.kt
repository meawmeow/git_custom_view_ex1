package cat.meaw.meow.mycustomsviewex1.components.canvas

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import cat.meaw.meow.mycustomsviewex1.R

//reference link https://www.raywenderlich.com/142-android-custom-view-tutorial

class EmotionalFaceView @JvmOverloads constructor(context: Context,attrs:AttributeSet):View(context, attrs) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val SAD = 1L
    }

    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH

    private val paint = Paint()
    private val mouthPath = Path()
    private var size = 0

    var colorCenterEye = Color.WHITE

    var happinessState = HAPPY
        set(state) {
            field = state
            // 4
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        // Obtain a typed array of attributes
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.EmotionalFaceView,
            0, 0
        )

        // Extract custom attributes into member variables
        happinessState =
            typedArray.getInt(R.styleable.EmotionalFaceView_state, HAPPY.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
        eyesColor = typedArray.getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_COLOR)
        mouthColor =
            typedArray.getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
        borderColor = typedArray.getColor(
            R.styleable.EmotionalFaceView_borderColor,
            DEFAULT_BORDER_COLOR
        )
        borderWidth = typedArray.getDimension(
            R.styleable.EmotionalFaceView_borderWidth,
            DEFAULT_BORDER_WIDTH
        )


        // TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size / 2f

        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftEyeRect, paint)

        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas.drawOval(rightEyeRect, paint)

        paint.color = colorCenterEye
        val leftCenterEyeRect = RectF(size * 0.58f, size * 0.30f, size * 0.66f, size * 0.40f)
        canvas.drawOval(leftCenterEyeRect, paint)

        val rightCenterEyeRect = RectF(size * 0.33f, size * 0.30f, size * 0.41f, size * 0.40f)
        canvas.drawOval(rightCenterEyeRect, paint)

    }


    @JvmName("setColorCenterEye1")
    fun setColorCenterEye(_colorEye:Int){
        colorCenterEye = _colorEye
        invalidate()
    }


    private fun drawMouth(canvas: Canvas) {
        // Clear
        mouthPath.reset()

        mouthPath.moveTo(size * 0.22f, size * 0.7f)

        if (happinessState == HAPPY) {
            // Happy mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
        } else {
            // Sad mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
        }

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        // Draw mouth path
        canvas.drawPath(mouthPath, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result =super.onTouchEvent(event)
        if(event.action == MotionEvent.ACTION_DOWN){
            Log.d("DSSW","ACTION_DOWN")
            colorCenterEye = Color.DKGRAY
            invalidate()
            return true
        }
        if(event.action == MotionEvent.ACTION_UP){
            Log.d("DSSW","ACTION_UP")
            colorCenterEye = Color.WHITE
            invalidate()
            return true
        }

        return result
    }
    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putLong("happinessState", happinessState)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var viewState = state
        if (viewState is Bundle) {
            happinessState = viewState.getLong("happinessState", HAPPY)
            viewState = viewState.getParcelable("superState")!!
        }
        super.onRestoreInstanceState(viewState)
    }
}