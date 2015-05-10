package perceptlab

import Game._
import math._

case class Point(x: Double = 0, y: Double = 0) {
  def scale(factor: Double) =
    Point(x * factor, y * factor)

  def translate(tx: Double, ty: Double): Point =
    if (tx != 0 || ty != 0)
      Point(x + tx, y + ty)
    else
      this

  lazy val local =
    Point(x % TileSize, y % TileSize)

  lazy val asPosition =
    IntegerPoint(floor(x / TileSize).toInt, floor(y / TileSize).toInt)

  lazy val tileCenterAsLocation =
    scale(TileSize).translate(TileCenter, TileCenter)

  def distance(other: Point) =
    math.sqrt(pow(other.x - x, 2) + pow(other.y - y, 2))

  def angle(other: Point) =
    - signum(other.y - y) * (acos((other.x - x) / distance(other)) % `2Pi`) + `2Pi`

  def toIntegerPoint =
    IntegerPoint(round(x).toInt, round(y).toInt)
}

case class IntegerPoint(x: Int = 0, y: Int = 0)