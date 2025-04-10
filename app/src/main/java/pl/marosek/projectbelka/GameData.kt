package pl.marosek.projectbelka

import kotlin.math.pow

data class GameScoreData (
    var score: Double = 0.0,
    var scorePerSecond: Double = 0.0,
    var scorePerClick: Double = 1.0,


    var upgrade1Bought: Int = 0,
    var upgrade2Bought: Int = 0,
    var upgrade3Bought: Int = 0,
    var upgrade4Bought: Int = 0,

    var upgrade1Price: Int = 25,
    var upgrade1Value: Int = 0,

    var upgrade2Price: Int = 100,
    var upgrade2Value: Int = 1,

    var upgrade3Price: Int = 750,
    var upgrade3Value: Double = 1.0,

    var upgrade4Price: Int = 6000,
//
//    var upgrade5Price: Int = 100000,
//    var upgrade5Value: Int = 10000

    var dummyPrice: Int = 250,
    var dummyValue: Int = 0,
)
{
    val scorePerClickInt: Int
        get() {
            val u1 = (scorePerClick + upgrade1Value)
            if (upgrade4Bought > 0) {
                return ((u1*u1).toInt())
            }
            return (u1).toInt()
        }

    val scorePerSecondInt: Double
        get() {
            val u2 = scorePerSecond * (upgrade2Value * upgrade3Value)
            return (u2)
        }
}
