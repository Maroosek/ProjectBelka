package pl.marosek.projectbelka

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView

class Bouncer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var dx = (-5..5).random().toFloat().coerceAtLeast(1f)
    private var dy = (-5..5).random().toFloat().coerceAtLeast(1f)
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            move()
            handler.postDelayed(this, 16) // ok. 60 FPS
        }
    }

    init {
        handler.post(runnable)
    }

    private fun move() {
        val parent = parent as? ViewGroup ?: return

        var newX = x + dx
        var newY = y + dy

        // granice kontenera
        val maxX = parent.width - width
        val maxY = parent.height - height

        // odbicia z losowością
        if (newX <= 0 || newX >= maxX) {
            dx *= -1
            dx += (-1..1).random() * 0.5f // lekka losowość po odbiciu
        }

        if (newY <= 0 || newY >= maxY) {
            dy *= -1
            dy += (-1..1).random() * 0.5f
        }

        // ogranicz maksymalną prędkość, żeby nie było teleportacji
        dx = dx.coerceIn(-10f, 10f)
        dy = dy.coerceIn(-10f, 10f)

        // zaktualizuj pozycję
        x += dx
        y += dy
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(runnable)
    }
}