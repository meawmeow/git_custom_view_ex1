package cat.meaw.meow.mycustomsviewex1.progress.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cat.meaw.meow.mycustomsviewex1.R
import kotlinx.android.synthetic.main.circle_progress_layout.view.*

class CircleProgressLayoutView(context: Context,attrs:AttributeSet):LinearLayout(context, attrs) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.circle_progress_layout,this,false)
        addView(view)
    }
    fun setProgressValue(value:Int){
        progressBar.progress = value
        progress_title.text = "$value%"
    }
}