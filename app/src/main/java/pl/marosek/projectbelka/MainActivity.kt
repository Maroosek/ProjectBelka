package pl.marosek.projectbelka

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import pl.marosek.projectbelka.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

val gameData = GameScoreData()

//TODO Fix layout to start from bottom, add some wacky animations :-D
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadGame()

        val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.US))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val idleScoreHandler = Handler(Looper.getMainLooper())

        idleScoreHandler.post(object : Runnable {
            override fun run() {
                gameData.score += (gameData.scorePerSecondInt)/10
                refreshText(binding.scoreTotal, df.format(gameData.score).toDouble())
                refreshText(binding.scorePerSecond, df.format(gameData.scorePerSecondInt).toDouble())
                //Toast.makeText(applicationContext, "1 second passed ${gameData.scorePerSecond}", Toast.LENGTH_SHORT).show()
                idleScoreHandler.postDelayed(this, 100)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun refreshText(text: TextView, number: Double) {
        text.text = number.toString()
    }

    private fun loadGame() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)

        if (!sharedPreferences.contains("score")) {
            //Toast.makeText(this, "Brak zapisanej gry", Toast.LENGTH_SHORT).show()
            return
        }

        gameData.score = sharedPreferences.getString("score", 0.toString())?.toDouble() ?: 0.0
        gameData.scorePerClick = sharedPreferences.getString("scorePerClick", 0.toString())?.toDouble() ?: 0.0
        gameData.scorePerSecond = sharedPreferences.getString("scorePerSecond", 0.toString())?.toDouble() ?: 0.0
        gameData.upgrade1Bought = sharedPreferences.getInt("upgrade1Bought", 0)
        gameData.upgrade2Bought = sharedPreferences.getInt("upgrade2Bought", 0)
        gameData.upgrade3Bought = sharedPreferences.getInt("upgrade3Bought", 0)
        gameData.upgrade1Price = sharedPreferences.getInt("upgrade1Price", 0)
        gameData.upgrade1Value = sharedPreferences.getInt("upgrade1Value", 0)
        gameData.upgrade2Price = sharedPreferences.getInt("upgrade2Price", 0)
        gameData.upgrade2Value = sharedPreferences.getInt("upgrade2Value", 0)
        gameData.upgrade3Price = sharedPreferences.getInt("upgrade3Price", 0)
        gameData.upgrade3Value = sharedPreferences.getString("upgrade3Value", 0.toString())?.toDouble() ?: 0.0

        //Toast.makeText(this, "Score: ${gameData.score}", Toast.LENGTH_SHORT).show()

        //afk time reward
        var timeAFK = (System.currentTimeMillis() / 1000).toInt() - sharedPreferences.getInt("lastTime", 0)
        if (timeAFK > 14440) { //4 hours of idle time
            timeAFK = 14440

        }
        val afkScore = timeAFK * 0.01 * gameData.scorePerSecondInt
        gameData.score += afkScore
        Toast.makeText(this, "Time AFK: $timeAFK Added points $afkScore", Toast.LENGTH_SHORT).show()
    }

    private fun saveGame() {

        val currentTime = System.currentTimeMillis() / 1000
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("score", gameData.score.toString())
        editor.putString("scorePerSecond", gameData.scorePerSecond.toString())
        editor.putString("scorePerClick", gameData.scorePerClick.toString())
        editor.putInt("upgrade1Bought", gameData.upgrade1Bought)
        editor.putInt("upgrade2Bought", gameData.upgrade2Bought)
        editor.putInt("upgrade3Bought", gameData.upgrade3Bought)
        editor.putInt("upgrade1Price", gameData.upgrade1Price)
        editor.putInt("upgrade1Value", gameData.upgrade1Value)
        editor.putInt("upgrade2Price", gameData.upgrade2Price)
        editor.putInt("upgrade2Value", gameData.upgrade2Value)
        editor.putInt("upgrade3Price", gameData.upgrade3Price)
        editor.putString("upgrade3Value", gameData.upgrade3Value.toString())
        editor.putInt("lastTime", currentTime.toInt())
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        loadGame()
    }

    override fun onPause() {
        super.onPause()
        saveGame()
    }

    override fun onStop() {
        super.onStop()
        saveGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveGame()
    }
}