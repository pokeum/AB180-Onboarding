package product.dp.io.ab180blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import product.dp.io.sdk.Airbridge

class CodingAssignmentActivity : AppCompatActivity() {

    private val button: Button
        get() = findViewById(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coding_assignment)

        button.setOnClickListener {
            Airbridge.trackEvent("coding_assignment", "button_click")
        }
    }
}