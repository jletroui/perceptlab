package perceptlab

import scala.io.Source
import Labyrinth._

case class Labyrinth(tiles: Seq[Seq[Tile]], playerStartingPosition: Point, objectivePosition: Point) {
  var playerDirection = 0.0
  var playerLocation = playerStartingPosition.scale(TileSize).translate(TileCenter, TileCenter)
}

case class Point(x: Int = 0, y: Int = 0) {
  def scale(factor: Int) =
    Point(x * factor, y * factor)
  def translate(tx: Int, ty: Int) =
    Point(x + tx, y + ty)
}

case class Tile(position: Point, isWall: Boolean) {
  lazy val location: Point = position.scale(TileSize)
}

object Labyrinth {
  val TileSize = 100
  val TileCenter = TileSize / 2

  def apply(resourcePath: String = "/default.lab"): Labyrinth =
    Source
      .fromInputStream(getClass.getClassLoader.getResourceAsStream(resourcePath))
      .getLines
      .toSeq
      .zipWithIndex
      .foldLeft(Labyrinth(Nil, Point(), Point())) { (laby, lineWithIndex) =>
        val (line, rIndex) = lineWithIndex
        val row = line.zipWithIndex.map { case (char, cIndex) =>
          if (char == '#')
            Tile(Point(cIndex, rIndex), isWall = true)
          else
            Tile(Point(cIndex, rIndex), isWall = false)
        }
        val playerColumn = line.indexOf('p')
        val objectiveColumn = line.indexOf('o')

        laby.copy(
          tiles = laby.tiles :+ row,
          playerStartingPosition = if (playerColumn >= 0) Point(playerColumn, rIndex) else laby.playerStartingPosition,
          objectivePosition = if (objectiveColumn >= 0) Point(objectiveColumn, rIndex) else laby.objectivePosition
        )
      }
}
