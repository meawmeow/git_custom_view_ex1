package cat.meaw.meow.mycustomsviewex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_to_progress.setOnClickListener {
            val intent = Intent(this,ProgressBarActivity::class.java)
            startActivity(intent)
        }
        btn_to_ui1.setOnClickListener {
            val intent = Intent(this,UI1Activity::class.java)
            startActivity(intent)
        }
        btn_to_emotion.setOnClickListener {
            val intent = Intent(this,EmotionalFaceActivity::class.java)
            startActivity(intent)
        }
    }
}