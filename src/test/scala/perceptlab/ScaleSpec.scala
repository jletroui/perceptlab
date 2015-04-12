package perceptlab

import org.scalatest.{Matchers, WordSpec}

class ScaleSpec extends WordSpec with Matchers {
  "The scale" should {
    "translate a Double range into a Int one" in {
      val sut = Scale(0.0 -> 300.0, 127 -> 0)

      sut.scale(150) shouldEqual 64
      sut.scale(0) shouldEqual 127
      sut.scale(300) shouldEqual 0
      sut.scale(-10) shouldEqual 127
      sut.scale(400) shouldEqual 0
    }
  }
}
