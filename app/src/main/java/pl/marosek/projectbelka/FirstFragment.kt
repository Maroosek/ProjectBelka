package pl.marosek.projectbelka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.ViewUtils.dpToPx
import pl.marosek.projectbelka.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val logoSize = 75

    var catList = listOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3,
        R.drawable.cat4, R.drawable.cat5, R.drawable.cat6, R.drawable.cat7,
        R.drawable.cat8, R.drawable.cat9, R.drawable.cat10, R.drawable.cat11,
        R.drawable.cat12, R.drawable.cat13, R.drawable.cat14, R.drawable.cat15
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gameData.score = 100000000.0

        if (gameData.dummyValue > 0){
            for (i in 0 until gameData.dummyValue) {
                addLogo(i)
            }
        }


        binding.debug.text = "Click value: ${gameData.scorePerClickInt}"


        if (gameData.dummyValue > 0) {
            binding.addDummyButton.text = gameData.dummyPrice.toString()
            if (gameData.dummyValue == 15) {
                binding.addDummyButton.isEnabled = false
                binding.addDummyButton.text = "Max"
            }
        }
        //binding.addDummyButton.text = gameData.dummyValue.toString()


        binding.scoreButton.setOnClickListener {
            gameData.score += gameData.scorePerClickInt
        }

        binding.upgradeMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.addDummyButton.setOnClickListener {
            if (gameData.score < gameData.dummyPrice) {
                Toast.makeText(context, "Not enough score", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Toast.makeText(context, "Dummy added ${gameData.dummyPrice}", Toast.LENGTH_SHORT).show()
            gameData.score -= gameData.dummyPrice
            gameData.dummyValue += 1
            gameData.dummyPrice = (gameData.dummyPrice * 1.5).toInt()

            if (gameData.dummyValue >= 15) {
                //Toast.makeText(context, "Already bought Max", Toast.LENGTH_SHORT).show()
                binding.addDummyButton.isEnabled = false
                binding.addDummyButton.text = "Max"
                addLogo(gameData.dummyValue - 1)
                //gameData.dummyValue = 15
                return@setOnClickListener
            }

            binding.addDummyButton.text = gameData.dummyPrice.toString()
            addLogo(gameData.dummyValue - 1)
        }
    }

    private fun addLogo(catNumber : Int = 0) {
        val logo = Bouncer(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(dpToPx(logoSize), dpToPx(logoSize))
            //setImageResource(R.mipmap.ic_launcher)

            //val randomCat = catList.random()
            val cat = catList[catNumber]

            setImageResource(cat)
            scaleType = ImageView.ScaleType.FIT_CENTER

            post {
                x = (0..(binding.bouncerContainer.width - width)).random().toFloat()
                y = (0..(binding.bouncerContainer.height - height)).random().toFloat()
            }
        }

        binding.bouncerContainer.addView(logo)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}