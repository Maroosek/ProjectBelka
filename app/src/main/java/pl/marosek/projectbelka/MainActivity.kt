package pl.marosek.projectbelka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //initial values
    var score = 0
    var scorePerSecond = 0

    var upgrade1Price = 10
    var upgrade1Value = 1
    var upgrade2Price = 100
    var upgrade2Value = 10
    var upgrade3Price = 1000
    var upgrade3Value = 100
    var upgrade4Price = 10000
    var upgrade4Value = 1000
    var upgrade5Price = 100000
    var upgrade5Value = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.buttonxd)
        var text = findViewById<TextView>(R.id.textView)
        var scoreTotalText = findViewById<TextView>(R.id.scoreTotal)
        var scorePerSecondText = findViewById<TextView>(R.id.scorePerSecond)
        var upgrade1text = findViewById<TextView>(R.id.upgradeText1)
        var upgrade1 = findViewById<Button>(R.id.buttonUpgrade1)
        var upgrade2text = findViewById<TextView>(R.id.upgradeText2)
        var upgrade2 = findViewById<Button>(R.id.buttonUpgrade2)
        var upgrade3text = findViewById<TextView>(R.id.upgradeText3)
        var upgrade3 = findViewById<Button>(R.id.buttonUpgrade3)
        var upgrade4text = findViewById<TextView>(R.id.upgradeText4)
        var upgrade4 = findViewById<Button>(R.id.buttonUpgrade4)
        var upgrade5text = findViewById<TextView>(R.id.upgradeText5)
        var upgrade5 = findViewById<Button>(R.id.buttonUpgrade5)


        //val delay = 1000

        loadGame()

        btn.setOnClickListener {
            //GameFunction().addNumber(upgrade1Value, scoreTotalText)
            addNumber(upgrade1Value, scoreTotalText)
            //Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            //refreshText(upgrade1text, upgrade1Price)
        }

        upgrade1.setOnClickListener {
            if (score >= upgrade1Price) {
            score -= upgrade1Price
            upgrade1Price *= 4
            upgrade1Value *= 2
            upgrade1text.text = upgrade1Price.toString()
            scoreTotalText.text = score.toString()
            addNumberPerSecond(upgrade1Value, scorePerSecondText)
            //upgrade1text.text = upgrade1Value.toString()
            //upgrade1text.text
        } else {
            Toast.makeText(this, "Not enough cash", Toast.LENGTH_SHORT).show()
        }
        }
    }

    override fun onStop() {
        super.onStop()
        saveGame()
    }

    override fun onPause() {
        super.onPause()
        saveGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveGame()
    }

    fun addNumber(number: Int, text: TextView) {
        score += number
        text.text = score.toString()
    }

    fun addNumberPerSecond(number: Int, text: TextView) {
        scorePerSecond += number / 10
        text.text = scorePerSecond.toString()
        refreshText(findViewById(R.id.scorePerSecond), scorePerSecond)
    }

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }

    fun loadGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 0)
        scorePerSecond = sharedPreferences.getInt("scorePerSecond", 0)
        upgrade1Price = sharedPreferences.getInt("upgrade1Price", 10)
        //Add other upgrades

        refreshText(findViewById(R.id.scoreTotal), score)
        refreshText(findViewById(R.id.scorePerSecond), scorePerSecond)
        refreshText(findViewById(R.id.upgradeText1), upgrade1Price)
        refreshText(findViewById(R.id.upgradeText2), upgrade2Price)
        refreshText(findViewById(R.id.upgradeText3), upgrade3Price)
        refreshText(findViewById(R.id.upgradeText4), upgrade4Price)
        refreshText(findViewById(R.id.upgradeText5), upgrade5Price)

    }

    fun saveGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("score", score)
        editor.putInt("scorePerSecond", scorePerSecond)
        editor.putInt("upgrade1Price", upgrade1Price)
        //Add other upgrades
        editor.apply()
    }
}