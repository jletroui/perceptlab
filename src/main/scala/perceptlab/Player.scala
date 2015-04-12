package perceptlab

import Player._

import scala.math._

case class Player(startingTile: Point) {
  var direction = 0.0
  var location = startingTile.tileCenterAsLocation

  def moveForward(max: Double, nanos: Long) = {
    val dist = min(nanos * MovingSpeedPerNano, max)
    location = location.translate(dist * cos(direction), - dist * sin(direction))
  }

  def turnLeft(nanos: Long) = {
    direction = direction + nanos * AngleSpeedPerNano
  }

  def turnRight(nanos: Long) =
    turnLeft(-nanos)
}

object Player {
  val MovingSpeed = 5           // pixels per second
  val AngleSpeed = math.Pi / 36 // radians per second
  val MovingSpeedPerNano = MovingSpeed / 1000000000.0
  val AngleSpeedPerNano = AngleSpeed / 1000000000.0
}