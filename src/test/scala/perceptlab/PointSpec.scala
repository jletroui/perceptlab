package perceptlab

import org.scalatest.{WordSpec, Matchers}
import math._

class PointSpec extends WordSpec with Matchers{
  "A point" should {
    "compute the right angle with an other point" in {
      Point(50, 50).angle(Point(0, 0)) shouldEqual normalized(3 * Pi / 4)
      Point(50, 50).angle(Point(100, 0)) shouldEqual normalized(Pi / 4)
      Point(50, 50).angle(Point(100, 100)) shouldEqual normalized(- Pi / 4)
      Point(50, 50).angle(Point(0, 100)) shouldEqual normalized(- 3 * Pi / 4)
    }
    "compute the tile position" in {
      Point(100, 100).asPosition shouldEqual IntegerPoint(1, 1)
      Point(101, 100).asPosition shouldEqual IntegerPoint(1, 1)
      Point(199, 100).asPosition shouldEqual IntegerPoint(1, 1)
      Point(200, 100).asPosition shouldEqual IntegerPoint(2, 1)
      Point(101, 101).asPosition shouldEqual IntegerPoint(1, 1)
      Point(199, 199).asPosition shouldEqual IntegerPoint(1, 1)
      Point(200, 200).asPosition shouldEqual IntegerPoint(2, 2)
    }
  }

  def normalized(angle: Double) =
    angle + 2 * Pi
}
