package cat.meaw.meow.mycustomsviewex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_progress_bar.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)

        circle_layout.setProgressValue(0)

        btn_start.setOnClickListener {

            progress_s2.startAnimation()
            progress_s3.startAnimation()

            lifecycleScope.launch {
                for(i in 1..100){
                    circle_layout.setProgressValue(i)
                    radial_progress.progress = i
                    delay(19)
                }
            }
        }
        btn_end.setOnClickListener {

            progress_s2.stopAnimation()
            progress_s3.stopAnimation()
            lifecycleScope.launch {
                for(i in 100 downTo 1){
                    circle_layout.setProgressValue(i)
                    radial_progress.progress = i
                    delay(19)
                }
            }
        }
    }
}