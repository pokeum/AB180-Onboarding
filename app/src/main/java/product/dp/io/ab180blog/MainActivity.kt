package product.dp.io.ab180blog

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import product.dp.io.sdk.CustomLog
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        /** Get Install Date */
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0);

            val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            val installDate = simpleDateFormat.format(packageInfo.firstInstallTime)
            val updateDate = simpleDateFormat.format(packageInfo.lastUpdateTime)

            //Toast.makeText(this, installDate, Toast.LENGTH_LONG).show()
            
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        CustomLog.debug(TAG, "onStart()")
    }

    override fun onStop() {
        super.onStop()
        CustomLog.debug(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomLog.debug(TAG, "onDestroy()")
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