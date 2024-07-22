package pl.marosek.projectbelka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //initial values
/*    var score = 0
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
    var upgrade5Value = 10000*/

    val game = GameFunction()
    val scoreData = GameScoreData()
    val upgradeData = GameUpgradeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var scoreButton = findViewById<Button>(R.id.scoreButton)
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

        loadGame()
        //startIdle(scorePerSecond, scoreTotalText)

        scoreButton.setOnClickListener {
            scoreData.score = game.addNumber(upgradeData.upgrade1Value, scoreData.score)

            scoreTotalText.text = scoreData.score.toString()
        }

        upgrade1.setOnClickListener {
            if (scoreData.score < upgradeData.upgrade1Price) {
            Toast.makeText(this, "Not enough cash", Toast.LENGTH_SHORT).show()

            return@setOnClickListener
            }
        scoreData.score -= upgradeData.upgrade1Price
        upgradeData.upgrade1Price *= 4
        upgradeData.upgrade1Value *= 2
        upgrade1text.text = upgradeData.upgrade1Price.toString()
        scoreTotalText.text = scoreData.score.toString()
        var dupa = game.addNumberPerSecond(upgradeData.upgrade1Value)
        //addNumberPerSecond(upgradeData.upgrade1Value, scorePerSecondText)
        //upgrade1text.text = upgrade1Value.toString()
        //upgrade1text.text
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

/*    fun addNumberPerSecond(number: Int, text: TextView) {
        scoreData.scorePerSecond += number / 10
        text.text = scoreData.scorePerSecond.toString()
        //refreshText(findViewById(R.id.scorePerSecond), scorePerSecond)
    }*/

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }

    fun loadGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        scoreData.score = sharedPreferences.getInt("score", 0)
        scoreData.scorePerSecond = sharedPreferences.getInt("scorePerSecond", 0)
        upgradeData.upgrade1Price = sharedPreferences.getInt("upgrade1Price", 10)
        //Add other upgrades

        refreshText(findViewById(R.id.scoreTotal), scoreData.score)
        refreshText(findViewById(R.id.scorePerSecond), scoreData.scorePerSecond)
        refreshText(findViewById(R.id.upgradeText1), upgradeData.upgrade1Price)
        refreshText(findViewById(R.id.upgradeText2), upgradeData.upgrade2Price)
        refreshText(findViewById(R.id.upgradeText3), upgradeData.upgrade3Price)
        refreshText(findViewById(R.id.upgradeText4), upgradeData.upgrade4Price)
        refreshText(findViewById(R.id.upgradeText5), upgradeData.upgrade5Price)

    }

    fun saveGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("score", scoreData.score)
        editor.putInt("scorePerSecond", scoreData.scorePerSecond)
        editor.putInt("upgrade1Price", upgradeData.upgrade1Price)
        editor.putInt("upgrade1Value", upgradeData.upgrade1Value)
        //Add other upgrades
        editor.apply()
    }
}