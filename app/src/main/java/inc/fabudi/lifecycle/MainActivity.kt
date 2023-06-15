package inc.fabudi.lifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var mStartForResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data!!.getBooleanExtra(
                "Update",
                false
            )
        ) findViewById<Tapper>(R.id.tapper).increaseRoundness()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Tapper>(R.id.tapper).setTapListener {
            mStartForResult.launch(Intent(this, UpdateActivity::class.java))
        }
    }

}