package pl.marosek.projectbelka

import kotlin.math.pow

data class GameScoreData (
    var score: Double = 0.0,
    var scorePerSecond: Double = 0.0,
    var scorePerClick: Double = 1.0,


    var upgrade1Bought: Int = 0,
    var upgrade2Bought: Int = 0,
    var upgrade3Bought: Int = 0,

    var upgrade1Price: Int = 25,
    var upgrade1Value: Int = 0,

    var upgrade2Price: Int = 100,
    var upgrade2Value: Int = 1,

    var upgrade3Price: Int = 750,
    var upgrade3Value: Double = 1.0,

//    var upgrade4Price: Int = 10000,
//    var upgrade4Value: Int = 1000,
//
//    var upgrade5Price: Int = 100000,
//    var upgrade5Value: Int = 10000
)
{
    val scorePerClickInt: Int
        get() {
            val u1 = scorePerClick + upgrade1Value
            return (u1).toInt()
        }

    val scorePerSecondInt: Double
        get() {
            val u2 = scorePerSecond * (upgrade2Value * upgrade3Value)
            return (u2)
        }
}
