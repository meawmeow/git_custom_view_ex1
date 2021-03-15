package cat.meaw.meow.mycustomsviewex1.components.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import cat.meaw.meow.mycustomsviewex1.R

class CustomEditTextVIew @JvmOverloads constructor(context: Context,attrs:AttributeSet):
    AppCompatEditText(context, attrs) {

    val mClearTextIcon: Drawable

    init {
        mClearTextIcon = context.resources.getDrawable(R.drawable.ic_baseline_clear_24)
        mClearTextIcon.setBounds(
            0, 0, mClearTextIcon.getIntrinsicHeight(),
            mClearTextIcon.getIntrinsicHeight()
        )
        setClearIconVisible(false)

        setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if (mClearTextIcon.isVisible && event.getX() > width - paddingRight - mClearTextIcon.intrinsicWidth) {
                    if (event.getAction() === MotionEvent.ACTION_UP) {
                        error = null
                        setText("")
                    }
                }
                return false
            }
        })
        setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                setClearIconVisible(text!!.length > 0);
            } else {
                setClearIconVisible(false);
            }
        }
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int, before: Int, count: Int
            ) {
                if (isFocused()) {
                    setClearIconVisible(s.length > 0);
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        //,View.OnTouchListener,View.OnFocusChangeListener,TextWatcher
    }

    private fun setClearIconVisible(visible: Boolean) {
        mClearTextIcon.setVisible(visible, false)
        val compoundDrawables = compoundDrawables
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            if (visible) mClearTextIcon else null,
            compoundDrawables[3]
        )
    }

}