package perceptlab

import Player._

import scala.math._

case class Player(location: Point, direction: Double) {
  def moveForward(maxAllowed: Double, millis: Long) = {
    val dist = min(millis * MovingSpeedPerMillis, max(maxAllowed, 0))
    val newLocation = location.translate(dist * cos(direction), - dist * sin(direction))
    if (dist != 0)
      println(s"$newLocation")
    copy(location = newLocation)
  }

  def turnLeft(millis: Long) = {
    val angularDist = millis * AngleSpeedPerMillis
      println(s"$direction + $angularDist")
    copy(direction = direction + angularDist)
  }

  def turnRight(millis: Long) =
    turnLeft(-millis)

  lazy val integerPosition = location.toIntegerPoint
}

object Player {
  val MovingSpeed = 5           // pixels per second
  val AngleSpeed = degrees(22) // radians per second
  val MovingSpeedPerMillis = MovingSpeed / 1000.0
  val AngleSpeedPerMillis = AngleSpeed / 1000.0

  def apply(startingTile: Point): Player =
    Player(startingTile.tileCenterAsLocation, 0.0)

  def degrees(degrees: Int) =
    degrees * Pi / 180.0
}