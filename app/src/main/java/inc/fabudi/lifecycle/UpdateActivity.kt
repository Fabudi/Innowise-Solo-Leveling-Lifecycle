package inc.fabudi.lifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updater)
        findViewById<Button>(R.id.update_button).setOnClickListener {
            val data = Intent()
            data.putExtra("Update", true)
            setResult(RESULT_OK, data)
            finish()
        }
        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            finish()
        }
    }
}