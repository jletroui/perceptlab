package perceptlab

import org.scalatest.{Matchers, WordSpec}

import math._

class TileCornerSpec extends WordSpec with Matchers {
  val normalizedFourthPi = 2 * Pi + Pi / 4 // 45 degrees
  val normalized7FourthPi = 2 * Pi + 7 * Pi / 4 // 315 degrees
  val normalized5FourthPi = 2 * Pi + 5 * Pi / 4 // 225 degrees

  "A tile corner" should {
    "correctly find the next location in top edge" in {
      val sut = TileCorner(Point(0, 0), Point(0, -1), Point(-1, -1))

      sut.nextLocation(Point(120, 120), normalizedFourthPi) shouldEqual Point(140, 100)
    }
    "correctly find the next location in bottom edge" in {
      val sut = TileCorner(Point(100, 100), Point(0, 1), Point(1, 1))

      sut.nextLocation(Point(120, 180), normalized7FourthPi) shouldEqual Point(140, 200)
    }
    "correctly find the next location in left edge" in {
      val sut = TileCorner(Point(0, 100), Point(-1, 0), Point(-1, 1))

      sut.nextLocation(Point(120, 120), normalized5FourthPi) shouldEqual Point(100, 140)
    }
    "correctly find the next location in right edge" in {
      val sut = TileCorner(Point(0, 100), Point(1, 0), Point(1, -1))

      sut.nextLocation(Point(180, 120), normalized7FourthPi) shouldEqual Point(200, 140)
    }
    "correctly find the next location vertically to top edge" in {
      val sut = TileCorner(Point(0, 0), Point(0, -1), Point(-1, -1))

      sut.nextLocation(Point(120, 120), Pi / 2) shouldEqual Point(120, 100)
    }
    "correctly find the next location vertically to bottom edge" in {
      val sut = TileCorner(Point(100, 100), Point(0, 1), Point(1, 1))

      sut.nextLocation(Point(120, 180), 3 * Pi / 2) shouldEqual Point(120, 200)
    }
    "correctly find the next location horizontally to left edge" in {
      val sut = TileCorner(Point(0, 100), Point(-1, 0), Point(-1, 1))

      sut.nextLocation(Point(120, 120), Pi) shouldEqual Point(100, 120)
    }
    "correctly find the next location horizontally to the right edge" in {
      val sut = TileCorner(Point(0, 100), Point(1, 0), Point(1, -1))

      sut.nextLocation(Point(180, 120), 2 * Pi) shouldEqual Point(200, 120)
    }
  }
}
