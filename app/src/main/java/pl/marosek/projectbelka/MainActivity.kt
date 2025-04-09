package pl.marosek.projectbelka

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
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
                gameData.score += gameData.scorePerSecond
                refreshText(binding.scoreTotal, gameData.score)
                //Toast.makeText(applicationContext, "1 second passed ${gameData.scorePerSecond}", Toast.LENGTH_SHORT).show()
                idleScoreHandler.postDelayed(this, 1000)
            }
        })

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}