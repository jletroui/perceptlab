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
  }

  def normalized(angle: Double) =
    angle + 2 * Pi
}
