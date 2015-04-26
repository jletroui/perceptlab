package perceptlab

import scala.annotation.tailrec

object Boot extends App {
  val laby = Labyrinth()
  val player = Player(laby.playerStartingTile)
  val scale = Scale(0.0 -> 300.0, 127 -> 0)

  using(SoundDisplay) { disp =>

    @tailrec
    def loop(previousNanos : Long = System.nanoTime()): Unit = {
      val distances = laby.sonarDistances(player)
      val scaled = distances.map(scale.scale)
      disp.setSonarLevels(scaled)
      val nanos = System.nanoTime()
      val elapsed = nanos - previousNanos

      loop(nanos)
    }

      loop()
  }

}
