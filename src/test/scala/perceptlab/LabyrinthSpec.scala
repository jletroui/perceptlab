package perceptlab

import org.scalatest.{Matchers, WordSpec}

import scala.math._

class LabyrinthSpec extends WordSpec with Matchers {
  val normalizedFourthPi = 2 * Pi + Pi / 4 // 45 degrees
  val Wall = true
  val Corridor = false

  // Sut is:
  //
  // ##
  //
  // ##

  val sut = Labyrinth(Seq(
    Seq(Tile(Point(0, 0), Wall),      Tile(Point(1, 0), Wall)),
    Seq(Tile(Point(0, 1), Corridor),  Tile(Point(1, 1), Corridor)),
    Seq(Tile(Point(0, 2), Wall),      Tile(Point(1, 2), Wall))
  ), Point(), Point())

  "The labyrinth" should {
    "give proper distance to wall through the current tile (top edge)" in {
      sut.sonarDistance(Point(10, 150), normalizedFourthPi) shouldEqual 50 * sqrt(2)
    }
    "give proper distance to wall through 2 tiles (right edge, then top edge)" in {
      sut.sonarDistance(Point(90, 150), normalizedFourthPi) shouldEqual 50 * sqrt(2)
    }
  }
}
