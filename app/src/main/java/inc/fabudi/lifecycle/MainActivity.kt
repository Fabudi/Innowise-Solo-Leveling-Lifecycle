package inc.fabudi.lifecycle

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: Editor

    private var mStartForResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data!!.getBooleanExtra(
                "Update", false
            )
        ) {
            Log.d("Radius", "Update: +10")
            findViewById<Tapper>(R.id.tapper).increaseRoundness(10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences("Lifecycle", MODE_PRIVATE)
        editor = preferences.edit()
        val tapper = findViewById<Tapper>(R.id.tapper)
        if (savedInstanceState != null) tapper.setRoundness(savedInstanceState.getInt("Radius", 0))
        else tapper.setRoundness(preferences.getInt("Radius", 0))
        Log.d("Test", "Saved value: " + preferences.getInt("Radius", 0).toString())
        tapper.setTapListener {
            mStartForResult.launch(Intent(this, UpdateActivity::class.java))
        }
        Log.d("Radius", "onCreate: " + findViewById<Tapper>(R.id.tapper).radius.toString())
    }

    override fun onResume() {
        super.onResume()
        val tapper = findViewById<Tapper>(R.id.tapper)
        tapper.setRoundness(tapper.radius)
        Log.d("Radius", "Total: " + findViewById<Tapper>(R.id.tapper).radius.toString())
    }

    override fun onPause() {
        super.onPause()
        editor.putLong("timeFrom", System.currentTimeMillis())
        editor.apply()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Radius", "Open up again: +2")
        val timePast = System.currentTimeMillis() - preferences.getLong("timeFrom", 0)
        Log.d("Radius", "You're late: -" + timePast / 1000 / 60 * 2)
        findViewById<Tapper>(R.id.tapper).increaseRoundness(2 - (timePast / 1000 / 60 * 2).toInt())
    }

    override fun onStop() {
        super.onStop()
        Log.d("Radius", "Close app: +5")
        editor.putInt("Radius", findViewById<Tapper>(R.id.tapper).radius)
        Log.d("Test", "Saving " + findViewById<Tapper>(R.id.tapper).radius)
        editor.apply()
        findViewById<Tapper>(R.id.tapper).increaseRoundness(5)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("Radius", findViewById<Tapper>(R.id.tapper).radius)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        editor.putLong("timeFrom", 0)
        editor.apply()
    }

}