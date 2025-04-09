package pl.marosek.projectbelka

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import pl.marosek.projectbelka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                refreshText(binding.scoreTotal, gameData.score)
                //Toast.makeText(applicationContext, "1 second passed ${gameData.scorePerSecond}", Toast.LENGTH_SHORT).show()
                idleScoreHandler.postDelayed(this, 100)
            }
        })

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun refreshText(text: TextView, number: Double) {
        text.text = number.toString()
    }

    //TODO rework save/load functions
    fun loadGame() {
        val game = GameFunction()
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        //gameData.score = sharedPreferences.getInt("score", 0)
        //gameData.scorePerSecond = sharedPreferences.getInt("scorePerSecond", 0)
        //afk time reward
        var timeAFK = (System.currentTimeMillis() / 1000).toInt() - sharedPreferences.getInt("lastTime", 0)
        if (timeAFK > 14440) { //4 hours of idle time
            timeAFK = 14440
        }
        Toast.makeText(this, "Time AFK: $timeAFK", Toast.LENGTH_SHORT).show()
        //gameData.score += game.addIdleScore(timeAFK, gameData.scorePerSecond)
        //Add other upgrades

        refreshText(findViewById(R.id.scoreTotal), gameData.score)
        refreshText(findViewById(R.id.scorePerSecond), gameData.scorePerSecond)
//        refreshText(findViewById(R.id.upgradeText1), upgradeData.upgrade1Price)
//        refreshText(findViewById(R.id.upgradeText2), upgradeData.upgrade2Price)
//        refreshText(findViewById(R.id.upgradeText3), upgradeData.upgrade3Price)
//        refreshText(findViewById(R.id.upgradeText4), upgradeData.upgrade4Price)
//        refreshText(findViewById(R.id.upgradeText5), upgradeData.upgrade5Price)
    }

    fun saveGame() {
        val currentTime = System.currentTimeMillis() / 1000
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        //editor.putInt("score", gameData.score)
        //editor.putInt("scorePerSecond", gameData.scorePerSecond)
//        editor.putInt("upgrade1Price", upgradeData.upgrade1Price)
//        editor.putInt("upgrade1Value", upgradeData.upgrade1Value)
//        editor.putInt("upgrade2Price", upgradeData.upgrade2Price)
//        editor.putInt("upgrade2Value", upgradeData.upgrade2Value)
        editor.putInt("lastTime", currentTime.toInt())
        //Add other upgrades
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        //loadGame()
    }
    override fun onPause() {
        super.onPause()
        //saveGame()
    }

    override fun onStop() {
        super.onStop()
        //saveGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        //saveGame()
    }
}