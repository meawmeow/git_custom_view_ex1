package cat.meaw.meow.mycustomsviewex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cat.meaw.meow.mycustomsviewex1.components.layout.CustomButtomView
import cat.meaw.meow.mycustomsviewex1.components.layout.RatingView
import kotlinx.android.synthetic.main.activity_u_i1.*
import kotlinx.coroutines.launch

class UI1Activity : AppCompatActivity(),RatingView.OnRateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_i1)

        btn_view.setActionProperties(object : CustomButtomView.OnActionClick{
            override fun onClick() {
                Toast.makeText(this@UI1Activity,"Show..", Toast.LENGTH_LONG).show()
                lifecycleScope.launch {
                    //btn_view.onAnimClick()
                }
            }
        })
        ratingBar.setRating(2)
        ratingBar.setRatingListener(this)
        Log.d("DSSH","${ratingBar.getRating()}")

        ratingBar.setIsRatingAllowed(true)
    }

    override fun onRated(ratingGiven: Int, totalRating: Int) {
        Log.d("DSSH","ratingGiven = $ratingGiven totalRating = $totalRating")
    }
}