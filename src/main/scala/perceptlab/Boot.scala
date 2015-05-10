package perceptlab

import java.util.concurrent.atomic.AtomicReference
import javax.swing.SwingUtilities

import scala.annotation.tailrec

object Boot extends App {
  val laby = Labyrinth()
  val player = new AtomicReference(Player(laby.playerStartingTile))
  val scale = Scale(0.0 -> 300.0, 127 -> 0)
  val minWallDistance = 2
  val ui = new PerceptLabUI(player)
  SwingUtilities.invokeLater(ui)

  using(SoundDisplay) { disp =>

      @tailrec
      def loop(previousMillis: Long = System.currentTimeMillis): Unit = {
        val distances = laby.sonarDistances(player.get)
        val scaled = distances.map(scale.scale)
        disp.setSonarLevels(scaled)

        val millis = System.currentTimeMillis
        val elapsed = millis - previousMillis

        if (elapsed < 9) println(elapsed)

        if (ui.keyboard.isUpPressed) {
          val maxAllowedWalkDistance = distances(Game.MidSonar) - minWallDistance
          player.getAndUpdate((p: Player) => p.moveForward(maxAllowedWalkDistance, elapsed))
         }

        if (ui.keyboard.isLeftPressed)
          player.getAndUpdate((p: Player) => p.turnLeft(elapsed))

        if (ui.keyboard.isRightPressed)
          player.getAndUpdate((p: Player) => p.turnRight(elapsed))

        Thread.sleep(20)

        if (!ui.keyboard.isEscapePressed)
          loop(millis)
      }


      loop()
  }
}
