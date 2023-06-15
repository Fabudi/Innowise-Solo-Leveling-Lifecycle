package inc.fabudi.lifecycle

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class Tapper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var roundRect: View
    private var radiusLabel: TextView
    private var tapButton: Button
    private var radius: Int = 0

    init {
        inflate(context, R.layout.tapper, this)
        roundRect = findViewById(R.id.round_rect)
        tapButton = findViewById(R.id.tap_button)
        radiusLabel = findViewById(R.id.radius_label)
        roundRect.background = getRoundDrawable(radius)
    }

    private fun getRoundDrawable(radius: Int): Drawable {
        val shape = PaintDrawable(Color.parseColor("#FFCC00"))
        shape.setCornerRadius(radius.toFloat())
        return shape
    }

    fun increaseRoundness() {
        radius += 10
        radiusLabel.text = radius.toString()
        roundRect.background = getRoundDrawable(radius)
    }

    fun setTapListener(onClickListener: OnClickListener) {
        tapButton.setOnClickListener(onClickListener)
    }
}