package pl.marosek.projectbelka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    //val scoreData = GameScoreData()
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

        val idleScoreHandler = Handler(Looper.getMainLooper())

        scorePerSecondText.text = gameData.scorePerSecond.toString()

        loadGame()

        idleScoreHandler.post(object : Runnable {
            override fun run() {
                gameData.score += gameData.scorePerSecond
                refreshText(scoreTotalText, gameData.score)
                //Toast.makeText(applicationContext, "1 second passed ${gameData.scorePerSecond}", Toast.LENGTH_SHORT).show()
                idleScoreHandler.postDelayed(this, 1000)
            }
        })


        scoreButton.setOnClickListener {
            gameData.score = game.addNumber(upgradeData.upgrade1Value, gameData.score)
            //Only upgrade 1 affects score per click
            scoreTotalText.text = gameData.score.toString()
        }

        upgrade1.setOnClickListener {
            if (gameData.score < upgradeData.upgrade1Price) {
            Toast.makeText(this, "Not enough cash", Toast.LENGTH_SHORT).show()

            return@setOnClickListener
            }
        gameData.score -= upgradeData.upgrade1Price
        upgradeData.upgrade1Price *= 4
        upgradeData.upgrade1Value *= 2
        upgrade1text.text = upgradeData.upgrade1Price.toString()
        scoreTotalText.text = gameData.score.toString()
        //var dupa = game.addNumberPerSecond(upgradeData.upgrade1Value)
        gameData.scorePerSecond += game.addNumberPerSecond(upgradeData.upgrade1Value)
        refreshText(scorePerSecondText, gameData.scorePerSecond)
        //upgrade1text.text = upgrade1Value.toString()
        //upgrade1text.text
        }

        upgrade2.setOnClickListener {
            if (gameData.score < upgradeData.upgrade2Price) {
            Toast.makeText(this, "Not enough cash", Toast.LENGTH_SHORT).show()

            return@setOnClickListener
            }
        gameData.score -= upgradeData.upgrade2Price
        upgradeData.upgrade2Price *= 4
        upgradeData.upgrade2Value *= 2
        upgrade2text.text = upgradeData.upgrade2Price.toString()
        scoreTotalText.text = gameData.score.toString()
        gameData.scorePerSecond += game.addNumberPerSecond(upgradeData.upgrade2Value)
        refreshText(scorePerSecondText, gameData.scorePerSecond)

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

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }

    fun loadGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        gameData.score = sharedPreferences.getInt("score", 0)
        gameData.scorePerSecond = sharedPreferences.getInt("scorePerSecond", 0)
        upgradeData.upgrade1Price = sharedPreferences.getInt("upgrade1Price", 10)
        upgradeData.upgrade1Value = sharedPreferences.getInt("upgrade1Value", 1)
        upgradeData.upgrade2Price = sharedPreferences.getInt("upgrade2Price", 100)
        upgradeData.upgrade2Value = sharedPreferences.getInt("upgrade2Value", 10)
        //afk time reward
        var timeAFK = (System.currentTimeMillis() / 1000).toInt() - sharedPreferences.getInt("lastTime", 0)
        if (timeAFK > 14440) { //4 hours of idle time
            timeAFK = 14440
        }
        Toast.makeText(this, "Time AFK: $timeAFK", Toast.LENGTH_SHORT).show()
        gameData.score += game.addIdleScore(timeAFK, gameData.scorePerSecond)
        //Add other upgrades

        refreshText(findViewById(R.id.scoreTotal), gameData.score)
        refreshText(findViewById(R.id.scorePerSecond), gameData.scorePerSecond)
        refreshText(findViewById(R.id.upgradeText1), upgradeData.upgrade1Price)
        refreshText(findViewById(R.id.upgradeText2), upgradeData.upgrade2Price)
        refreshText(findViewById(R.id.upgradeText3), upgradeData.upgrade3Price)
        refreshText(findViewById(R.id.upgradeText4), upgradeData.upgrade4Price)
        refreshText(findViewById(R.id.upgradeText5), upgradeData.upgrade5Price)

    }

    fun saveGame() {
        val currentTime = System.currentTimeMillis() / 1000
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("score", gameData.score)
        editor.putInt("scorePerSecond", gameData.scorePerSecond)
        editor.putInt("upgrade1Price", upgradeData.upgrade1Price)
        editor.putInt("upgrade1Value", upgradeData.upgrade1Value)
        editor.putInt("upgrade2Price", upgradeData.upgrade2Price)
        editor.putInt("upgrade2Value", upgradeData.upgrade2Value)
        editor.putInt("lastTime", currentTime.toInt())
        //Add other upgrades
        editor.apply()
    }
}