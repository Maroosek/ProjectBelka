package pl.marosek.projectbelka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pl.marosek.projectbelka.databinding.FragmentSecondBinding
import kotlin.math.pow

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.upgradeClickButton.text = gameData.upgrade1Price.toString()
        binding.upgradeIdleButton.text = gameData.upgrade2Price.toString()
        binding.upgradeIdlePercentageButton.text = gameData.upgrade3Price.toString()
        binding.upgradeClickDoubleButton.text = gameData.upgrade4Price.toString()

        checkUpgrades()

        binding.upgradeClickButton.setOnClickListener {
            //val gameUpgradeData = GameUpgradeData()

            if (gameData.score < gameData.upgrade1Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "Dupa: $dupa Score: ${gameScoreData.score} upgrade: ${gameUpgradeData.upgrade1Price}", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade1Price
            gameData.scorePerClick += gameData.upgrade1Value

            gameData.upgrade1Bought += 1
            gameData.upgrade1Price = 25 * 2.0.pow(gameData.upgrade1Bought.toDouble()).toInt()
            gameData.upgrade1Value += 1

            if (gameData.upgrade1Bought >= 5) {
                //Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                refreshText(binding.upgradeClickButton, "Max")
                binding.upgradeClickButton.isEnabled = false
                return@setOnClickListener
            }

            //refreshText(binding.upgradeClickButton, gameData.upgrade1Price)
            refreshText(binding.upgradeClickButton, gameData.upgrade1Price.toString())
        }

        binding.upgradeIdleButton.setOnClickListener {

            if (gameData.score < gameData.upgrade2Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "Dupa: $dupa Score: ${gameScoreData.score} upgrade: ${gameUpgradeData.upgrade1Price}", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade2Price
            gameData.scorePerSecond += gameData.upgrade2Value

            if (gameData.upgrade2Bought == 0) {
                gameData.upgrade2Bought += 1
                gameData.scorePerSecond += 1
                gameData.upgrade2Price = gameData.upgrade2Price * 2.0.pow(gameData.upgrade2Bought.toDouble()).toInt()
                refreshText(binding.upgradeIdleButton, gameData.upgrade2Price.toString())
                return@setOnClickListener
            }

            gameData.upgrade2Bought += 1
            gameData.upgrade2Price = 100 * 2.0.pow(gameData.upgrade2Bought.toDouble()).toInt()
            gameData.upgrade2Value *= 2

            if (gameData.upgrade2Bought >= 5) {
                //Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                refreshText(binding.upgradeIdleButton, "Max")
                binding.upgradeIdleButton.isEnabled = false
                return@setOnClickListener
            }

            refreshText(binding.upgradeIdleButton, gameData.upgrade2Price.toString())
            //binding.upgradeText1.text = "Upgrade 1: ${upgradeData.upgrade1Price} score, +${upgradeData.upgrade1Value} score per second"
        }

        binding.upgradeIdlePercentageButton.setOnClickListener {
            if (gameData.score < gameData.upgrade3Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade3Price
            gameData.scorePerSecond += gameData.upgrade3Value

            gameData.upgrade3Bought += 1
            gameData.upgrade3Price = 750 * 2.5.pow(gameData.upgrade3Bought.toDouble()).toInt()
            gameData.upgrade3Value += 0.1

            if (gameData.upgrade3Bought >= 5) {
                //Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                refreshText(binding.upgradeIdlePercentageButton, "Max")
                binding.upgradeIdlePercentageButton.isEnabled = false
                return@setOnClickListener
            }
            refreshText(binding.upgradeIdlePercentageButton, gameData.upgrade3Price.toString())
        }

        binding.upgradeClickDoubleButton.setOnClickListener {
            if (gameData.score < gameData.upgrade4Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade4Price
            gameData.upgrade4Bought += 1

            //Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
            refreshText(binding.upgradeClickDoubleButton, "Max")
            binding.upgradeClickDoubleButton.isEnabled = false
        }
    }

    private fun refreshText(text: TextView, content: String) {
        text.text = content
    }

    private fun checkUpgrades(){
        if (gameData.upgrade1Bought >= 5) {
            binding.upgradeClickButton.isEnabled = false
            binding.upgradeClickButton.text = "Max"
        }
        if (gameData.upgrade2Bought >= 5) {
            binding.upgradeIdleButton.isEnabled = false
            binding.upgradeIdleButton.text = "Max"
        }
        if (gameData.upgrade3Bought >= 5) {
            binding.upgradeIdlePercentageButton.isEnabled = false
            binding.upgradeIdlePercentageButton.text = "Max"
        }
        if (gameData.upgrade4Bought >= 1) {
            binding.upgradeClickDoubleButton.isEnabled = false
            binding.upgradeClickDoubleButton.text = "Max"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}