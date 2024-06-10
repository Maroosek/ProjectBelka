package pl.marosek.projectbelka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.buttonxd)
        var text = findViewById<TextView>(R.id.textView)
        var upgrade1text = findViewById<TextView>(R.id.upgradeText1)
        var upgrade1 = findViewById<Button>(R.id.buttonUpgrade1)
        var upgrade1Price = 10
        var upgrade1Value = 1
        var upgrade2text = findViewById<TextView>(R.id.upgradeText2)
        var upgrade2 = findViewById<Button>(R.id.buttonUpgrade2)
        var upgrade2Price = 100
        var upgrade2Value = 10
        var upgrade3text = findViewById<TextView>(R.id.upgradeText3)
        var upgrade3 = findViewById<Button>(R.id.buttonUpgrade3)
        var upgrade3Price = 1000
        var upgrade3Value = 100
        var upgrade4text = findViewById<TextView>(R.id.upgradeText4)
        var upgrade4 = findViewById<Button>(R.id.buttonUpgrade4)
        var upgrade4Price = 10000
        var upgrade4Value = 1000
        var upgrade5text = findViewById<TextView>(R.id.upgradeText5)
        var upgrade5 = findViewById<Button>(R.id.buttonUpgrade5)
        var upgrade5Price = 100000
        var upgrade5Value = 10000

        val delay = 1000

        btn.setOnClickListener {
            addNumber(upgrade1Value, text)
            Toast.makeText(this, "Kurwa", Toast.LENGTH_SHORT).show()
        }
    }

    fun addNumber(number: Int, text: TextView) {
        score += number
        text.text = score.toString()
    }

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }
}