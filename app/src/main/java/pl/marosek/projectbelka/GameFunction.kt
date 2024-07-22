package pl.marosek.projectbelka

import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.delay
val gameData = GameScoreData()
class GameFunction {
    fun addNumber(number: Int, score: Int): Int {
        var returnScore = score + number
        return returnScore
    }
    fun addNumberPerSecond(number: Int): Int {
        return number / 10
    }
/*    fun buyUpgrade(score: Int, upgradePrice: Int, upgradeValue: Int) {
        score -= upgradePrice
        upgradePrice *= 4
        upgradeValue *= 2
    }*/
fun startIdle(score : Int, scoreTotalText : TextView) {

    gameData.score += gameData.scorePerSecond

}
}