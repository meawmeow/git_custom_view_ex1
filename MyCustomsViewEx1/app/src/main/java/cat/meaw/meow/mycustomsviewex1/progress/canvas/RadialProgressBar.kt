package cat.meaw.meow.mycustomsviewex1.progress.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import cat.meaw.meow.mycustomsviewex1.R

class RadialProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet):
    ProgressBar(context, attrs) {

    private val thickness = 45f
    private val halfThickness = thickness / 2
    private val startAngle = 270f
    var boundsF: RectF? = null
    private lateinit var paint: Paint

    init {
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = thickness
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = ContextCompat.getColor(context, R.color.teal_200)

        progressDrawable = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (boundsF == null) {
            boundsF = RectF(background.bounds)
            boundsF!!.inset(halfThickness, halfThickness)
        }

        canvas.drawArc(boundsF!!, startAngle, progress * 3.60f, false, paint)

    }

}