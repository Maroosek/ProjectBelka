package pl.marosek.projectbelka

val gameData = GameScoreData()
class GameFunction {
    fun addNumber(number: Int, score: Int): Int {
        val returnScore = score + number
        return returnScore
    }
    fun addNumberPerSecond(number: Int): Int {
        return number / 10
    }
    fun addIdleScore(time: Int, scorePerSecond: Int): Int { //function to calculate score when app is closed
        return time * (scorePerSecond / 10)
    }
/*    fun buyUpgrade(score: Int, upgradePrice: Int, upgradeValue: Int) {
        score -= upgradePrice
        upgradePrice *= 4
        upgradeValue *= 2
    }*/
}