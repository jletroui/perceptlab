package perceptlab

import Game._
import math._

case class Point(x: Int = 0, y: Int = 0) {
  def scale(factor: Int) =
    Point(x * factor, y * factor)

  def translate(tx: Int, ty: Int): Point =
    Point(x + tx, y + ty)

  def translate(tx: Double, ty: Double): Point =
    translate(round(tx).toInt, round(ty).toInt)

  lazy val local =
    Point(x % TileSize, y % TileSize)

  lazy val asPosition =
    Point(x / TileSize, y / TileSize)

  lazy val tileCenterAsLocation =
    scale(TileSize).translate(TileCenter, TileCenter)

  def distance(other: Point) =
    math.sqrt(pow(other.x - x, 2) + pow(other.y - y, 2))

  def angle(other: Point) =
    - signum(other.y - y) * (acos((other.x - x) / distance(other)) % `2Pi`) + `2Pi`
}

