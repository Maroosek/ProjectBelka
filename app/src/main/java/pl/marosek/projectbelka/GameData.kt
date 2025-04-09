package pl.marosek.projectbelka

data class GameScoreData (
    var score: Int = 0,
    var scorePerSecond: Int = 0,
    var scorePerClick: Int = 1,


    var upgrade1Bought: Int = 0,
    var upgrade2Bought: Int = 0,

    var upgrade1Price: Int = 10,
    var upgrade1Value: Int = 1,
    var upgrade2Price: Int = 100,
    var upgrade2Value: Int = 10,

    var upgrade3Price: Int = 1000,
    var upgrade3Value: Int = 100,
    var upgrade4Price: Int = 10000,
    var upgrade4Value: Int = 1000,
    var upgrade5Price: Int = 100000,
    var upgrade5Value: Int = 10000
)
