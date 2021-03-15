package cat.meaw.meow.mycustomsviewex1.components.layout

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import cat.meaw.meow.mycustomsviewex1.R
import kotlinx.android.synthetic.main.item_button.view.*

class CustomButtomView(context: Context,attrs:AttributeSet? = null):RelativeLayout(context, attrs) {

    var position_icon = 0f
    interface OnActionClick{
        fun onClick()
    }
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_button, this, false)
        addView(view)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ButtonView)
        imv.setImageDrawable(attributes.getDrawable(R.styleable.ButtonView_buttomIcon))
        txt_title.text = attributes.getString(R.styleable.ButtonView_buttonTitle)
        txt_title.setTextColor(Color.WHITE)
        la_root.background = attributes.getDrawable(R.styleable.ButtonView_buttonBg)

        attributes.recycle()

        val xx = getChildAt(0)
        Log.d("DSS", "TextView = ${xx.layoutParams.width}")
    }

    fun setActionProperties(onActionClick: OnActionClick){
        la_root.setOnClickListener {
            la_root.isClickable = false
            onActionClick.onClick()
            onAnimClick()
            Log.d("DSS", "CLICK...")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var childWidth = 0
        if(widthSpecMode == MeasureSpec.AT_MOST){
            childWidth = MeasureSpec.getSize(widthMeasureSpec);
            Log.d("DSS", "AT_MOST = $childWidth")
        }
        else if(widthSpecMode == MeasureSpec.EXACTLY){
            childWidth = MeasureSpec.getSize(widthMeasureSpec);
            Log.d("DSS", "EXACTLY = $childWidth")
        }
        else {

        }
        val size = Math.max(measuredWidth, measuredHeight)
        position_icon = (size.toFloat() - imv.measuredWidth)-(paddingRight+imv.paddingLeft)

        //setMeasuredDimension(size, size)
//        val pleft= imv.paddingLeft
//        val pRight = imv.paddingRight
//        Log.d("DSS", "onMeasure position_icon = $position_icon widthSpecMode = $widthSpecMode cal = ${childWidth-imv.measuredWidth} padLeft = ${pleft} pRight = ${pRight}")
    }

    fun onAnimClick(){
//        la_root.isClickable = false
//        imv.animate().translationXBy(200f).duration = 500L
//        delay(1000L)
//        imv.animate().translationXBy(-200f).duration = 500L
//        la_root.isClickable = true

        val animator = ObjectAnimator.ofFloat(imv, View.TRANSLATION_X, position_icon)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.duration = 500
        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                la_root.isClickable = true
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })


    }
}