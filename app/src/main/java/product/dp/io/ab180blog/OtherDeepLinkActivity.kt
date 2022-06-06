package product.dp.io.ab180blog

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class OtherDeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_deep_link)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        findViewById<TextView>(R.id.textViewAction)?.text = action
        findViewById<TextView>(R.id.textViewData)?.text = data.toString()
    }
}