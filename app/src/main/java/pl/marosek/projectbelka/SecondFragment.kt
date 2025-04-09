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
//TODO tone down the upgrade values to slow down the game
/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        //binding.upgradeClickButton.text = gameData.upgrade1Price.toString()
        binding.upgradeClickText.text = "Price: ${gameData.upgrade1Price}"
        binding.upgradeIdleText.text = "Price: ${gameData.upgrade2Price}"
        binding.upgradeIdlePercentageText.text = "Price: ${gameData.upgrade3Price}"

        binding.upgradeClickButton.setOnClickListener {
            //val gameUpgradeData = GameUpgradeData()

            if (gameData.score < gameData.upgrade1Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "Dupa: $dupa Score: ${gameScoreData.score} upgrade: ${gameUpgradeData.upgrade1Price}", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gameData.upgrade1Bought >= 6) {
                Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade1Price
            gameData.scorePerClick += gameData.upgrade1Value

            gameData.upgrade1Bought += 1
            gameData.upgrade1Price = 25 * 1.5.pow(gameData.upgrade1Bought.toDouble()).toInt()
            //gameData.upgrade1Value *= 2

            //refreshText(binding.upgradeClickButton, gameData.upgrade1Price)
            refreshText(binding.upgradeClickText, gameData.upgrade1Price)
        }

        binding.upgradeIdleButton.setOnClickListener {

            if (gameData.score < gameData.upgrade2Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "Dupa: $dupa Score: ${gameScoreData.score} upgrade: ${gameUpgradeData.upgrade1Price}", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gameData.upgrade2Bought >= 5) {
                Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade2Price
            gameData.scorePerSecond += gameData.upgrade2Value

            gameData.upgrade2Bought += 1
            gameData.upgrade2Price = 100 * 1.5.pow(gameData.upgrade2Bought.toDouble()).toInt()
            gameData.upgrade2Value *= 2

            refreshText(binding.upgradeIdleText, gameData.upgrade2Price)

            //binding.upgradeText1.text = "Upgrade 1: ${upgradeData.upgrade1Price} score, +${upgradeData.upgrade1Value} score per second"
        }

        binding.upgradeIdlePercentageButton.setOnClickListener {
            if (gameData.score < gameData.upgrade3Price) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gameData.upgrade3Bought >= 5) {
                Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gameData.score -= gameData.upgrade3Price
            gameData.scorePerSecond += gameData.upgrade3Value

            gameData.upgrade3Bought += 1
            gameData.upgrade3Price = 750 * 1.4.pow(gameData.upgrade3Bought.toDouble()).toInt()
            gameData.upgrade3Value *= 2

            refreshText(binding.upgradeIdlePercentageText, gameData.upgrade3Price)
        }
    }

    fun refreshText(text: TextView, number: Int) {
        text.text = number.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}