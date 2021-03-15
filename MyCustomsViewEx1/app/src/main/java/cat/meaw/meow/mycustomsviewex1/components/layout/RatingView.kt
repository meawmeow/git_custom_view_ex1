package cat.meaw.meow.mycustomsviewex1.components.layout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import cat.meaw.meow.mycustomsviewex1.R

//reference link https://medium.com/android-news/app-rating-bar-making-a-compound-viewgroup-in-android-adb2bd25f4cc

class RatingView @JvmOverloads constructor(context: Context, attrs: AttributeSet): LinearLayout(
    context,
    attrs
) {

    private var mNumStars = 5
    private var mRating = 0
    private var mUnFirstRating = -1
    private var mRatingAllowed = false
    private var ratingOngoing = false

    var onRateListener: OnRateListener? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        setIsRatingAllowed(true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingView)
        mNumStars = typedArray.getInteger(R.styleable.RatingView_numStars, mNumStars)
        mRating = typedArray.getInteger(R.styleable.RatingView_rating, mRating)
        typedArray.recycle()

        addRatingStars()
        updateViewAfterRatingChanged(mRating - 1)

    }

    private fun addRatingStars() {
        if (mNumStars != 0) {
            for (i in 0 until mNumStars) {
                addView(getRatingView())
            }
        }
    }

    fun getRatingView(): CheckBox {
        val checkBox = LayoutInflater.from(context).inflate(R.layout.rating_star_item, this, false) as CheckBox
        checkBox.id = childCount
        //Log.d("DSSH","checkBox.id = ${checkBox.id} childCount = $childCount")
        checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (ratingOngoing) {
                    return
                }
                if (buttonView.id >= 0 && buttonView.id <= childCount - 1) {
                    //Log.d("DSSH","updateView ${childCount - 1} id = ${buttonView.id}")
                    if(buttonView.id == 0){
                        mUnFirstRating++
                    }else{
                        mUnFirstRating = -1
                    }
                    if(mUnFirstRating==1){
                        updateViewAfterRatingChanged(-1)
                        mUnFirstRating=-1
                    }else{
                        updateViewAfterRatingChanged(buttonView.id)
                    }

                }
            }
        })
        return checkBox
    }

    fun setRatingListener(_onRateListener: OnRateListener) {
        onRateListener = _onRateListener
    }


    private fun updateViewAfterRatingChanged(checkedPosition: Int) {
        mRating = checkedPosition + 1

        ratingOngoing = true
        if (checkedPosition < childCount) {
            for (i in 0 until childCount) {
                (getChildAt(i) as CheckBox).isChecked = i <= checkedPosition
            }
            onRateListener?.onRated(checkedPosition + 1, childCount)
        }
        ratingOngoing = false
    }

    fun setIsRatingAllowed(_mRatingAllowed: Boolean) {
        mRatingAllowed = _mRatingAllowed
        setRatingStarsCheckAllowed()
    }

    private fun setRatingStarsCheckAllowed() {
        if (childCount != 0) {
            for (i in 0 until childCount) {
                val cb = getChildAt(i) as CheckBox
                cb.isEnabled = mRatingAllowed
            }
        }
    }
    fun setRating(mRating: Int) {
        this.mRating = mRating
        updateViewAfterRatingChanged(this.mRating - 1)
    }

    fun getRating(): Int {
        return mRating
    }

    interface OnRateListener {
        fun onRated(ratingGiven: Int, totalRating: Int)
    }
}