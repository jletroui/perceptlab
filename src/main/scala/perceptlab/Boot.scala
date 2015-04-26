package perceptlab

import scala.annotation.tailrec

object Boot extends App {
  private val laby = Labyrinth()
  private val player = Player(laby.playerStartingTile)
  private val scale = Scale(0.0 -> 300.0, 127 -> 0)
  private val minWallDistance = 2

  using(SoundDisplay) { disp =>
    using(Keyboard) { keyboard =>

      @tailrec
      def loop(previousNanos: Long = System.nanoTime()): Unit = {
        val distances = laby.sonarDistances(player)
        val scaled = distances.map(scale.scale)
        disp.setSonarLevels(scaled)

        val nanos = System.nanoTime()
        val elapsed = nanos - previousNanos

        if (keyboard.isUpPressed) {
          val maxAllowedWalkDistance = distances(Game.MidSonar) - minWallDistance
          player.moveForward(maxAllowedWalkDistance, elapsed)
         }

        if (keyboard.isLeftPressed)
          player.turnLeft(elapsed)

        if (keyboard.isRightPressed)
          player.turnRight(elapsed)

        if (!keyboard.isEscapePressed)
          loop(nanos)
      }

      loop()

    }
  }
}
