package perceptlab

import math._

object Game {
  val `2Pi` = 2 * math.Pi
  val TileSize = 100
  val TileCenter = TileSize / 2
  val SonarNumber = 5
  val MidSonar = round(5.0 / 2).toInt
  val SonarAngleOffsets = {
    val increment = math.Pi / (SonarNumber - 1)
    Seq.tabulate(SonarNumber) { i =>
      math.Pi / 2 - i * increment
    }
  }
}
