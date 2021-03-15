package cat.meaw.meow.mycustomsviewex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cat.meaw.meow.mycustomsviewex1.components.canvas.EmotionalFaceView
import kotlinx.android.synthetic.main.activity_emotional_face.*

class EmotionalFaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotional_face)

        happyButton.setOnClickListener {
            emotionalFaceView.happinessState = EmotionalFaceView.HAPPY
        }
        sadButton.setOnClickListener {
            emotionalFaceView.happinessState = EmotionalFaceView.SAD
        }
    }
}