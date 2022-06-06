package product.dp.io.ab180blog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import product.dp.io.sdk.CustomLog
import product.dp.io.sdk.Lifecycle

class DeepLinkActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DeepLinkActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

        CustomLog.debug(TAG, "onCreate()")

        val action: String? = intent?.action
        val data: Uri? = intent?.data
        val categories: Set<String>? = intent?.categories

        val sbCategories = StringBuilder()
        if (categories != null) {
            for (cat: String in categories) {
                sbCategories.append("$cat\n")
            }
        } else { sbCategories.append("No Category") }

        findViewById<TextView>(R.id.textViewAction)?.text = action ?: "No Action"
        findViewById<TextView>(R.id.textViewData)?.text = data?.toString() ?: "No Data"
        findViewById<TextView>(R.id.textViewCategories)?.text = sbCategories?.toString()

        findViewById<Button>(R.id.btnLinkSelf).setOnClickListener {
            deepLinkIntent("https://go.ab180.co")
        }
        findViewById<Button>(R.id.btnLinkMain).setOnClickListener {
            deepLinkIntent("main://singleTask")
        }
    }

    override fun onStart() {
        super.onStart()
        CustomLog.debug(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.textViewResume).text =
            "onResume()-DEEPLINK_OPENED: ${
                intent?.getBooleanExtra(Lifecycle.DEEPLINK_OPENED, false)}"
        CustomLog.debug(TAG, "onResume()")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        findViewById<TextView>(R.id.textViewNewIntent).text =
            "onNewIntent()-DEEPLINK_OPENED: ${
                intent?.getBooleanExtra(Lifecycle.DEEPLINK_OPENED, false)}"
        CustomLog.debug(TAG, "onNewIntent()")
    }

    override fun onPause() {
        super.onPause()
        CustomLog.debug(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        CustomLog.debug(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomLog.debug(TAG, "onDestroy()")
    }

    private fun deepLinkIntent(uriString: String) {

        val uri = Uri.parse(uriString)
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = uri
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.launch_mode_singleTop -> {
                val intent = Intent(this, DeepLinkActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.launch_mode_singleTask -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.coding_assignment -> {
                val intent = Intent(this, CodingAssignmentActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}