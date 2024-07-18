package pl.marosek.projectbelka

import android.widget.TextView
import android.widget.Toast

class GameFunction {
    fun addNumber(number: Int, text: TextView) { //not working
        MainActivity().score += number
        //Toast.makeText(MainActivity(), number.toString(), Toast.LENGTH_SHORT).show()
        text.text = MainActivity().score.toString()
    }
}