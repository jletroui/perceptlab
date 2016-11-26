package perceptlab

import scala.annotation.tailrec
import scala.io.Source
import Game._
import Labyrinth._
import math._
import scala.util.control.NonFatal

case class Labyrinth(tiles: Seq[Seq[Tile]], playerStartingTile: Point, objectiveTile: Point) {
  def sonarDistances(player: Player): Seq[Double] =
    SonarAngleOffsets.map { offset =>
//      println(s"\nTreating angle offset $offset")
      sonarDistance(player.location, normalized(player.direction + offset))
    }

  @tailrec
  final def sonarDistance(location: Point, normalizedAngle: Double, distance: Double = 0): Double = {
      val quadrant = TileCorners
        .map(corner => Quadrant(corner, location.local.angle(corner.localLocation)))
        .sliding(2)
        .find { case Seq(currentQuadrant, nextQuadrant) =>
        if (currentQuadrant.angle > nextQuadrant.angle)
          currentQuadrant.angle >= normalizedAngle && normalizedAngle > nextQuadrant.angle
        else
          currentQuadrant.angle >= normalizedAngle || normalizedAngle > nextQuadrant.angle
      }
        .get
        .head
//      println(s"Angle: ${math.round(normalizedAngle / 2 / Pi * 360) - 360}")
//      println(s"Quadrant: $quadrant")

      val tileOffset =
        quadrant.nextTileOffset(normalizedAngle)

    val nextTile = try {
        tiles(location.asPosition.y + tileOffset.y)(location.asPosition.x + tileOffset.x)
    }
    catch {
      case ex: Throwable =>
        println(s"Location: $location Distance: $distance")
        println(s"Angle: ${math.round(normalizedAngle / 2 / Pi * 360) - 360}")
        println(s"Quadrant: $quadrant")
        throw ex
    }

//      println(s"Next tile: $nextTile")
    val nextLocation =
      quadrant.nextLocation(location, normalizedAngle)

    if (nextLocation.x < 0 || nextLocation.y < 0) {
      println(s"Location: $location Distance: $distance")
      println(s"Angle: ${math.round(normalizedAngle / 2 / Pi * 360) - 360}")
      println(s"Quadrant: $quadrant")
      println(s"Next tile: $nextTile")
      println(s"Next location: $nextLocation")
      throw new Exception("Oh oh. Next location is invalid.")
    }


//      println(s"Next location: $nextLocation")
    val newDistance =
      distance + nextLocation.distance(location)

//      println(s"New distance: $newDistance")
    if (nextTile.isWall)
      newDistance
    else
      sonarDistance(nextLocation, normalizedAngle, newDistance)
  }

  private def normalized(angle: Double) =
    (angle % `2Pi`) + `2Pi`
}

case class Tile(position: Point, isWall: Boolean)

case class TileCorner(localLocation: Point, nextTileOffset: IntegerPoint, cornerOffset: IntegerPoint) {
  val isTop = nextTileOffset.y == -1
  val isBottom = nextTileOffset.y == 1
  val isLeft = nextTileOffset.x == -1
  val isRight = nextTileOffset.x == 1

  def nextLocation(globalPoint: Point, angle: Double): Point = {
    val local  = globalPoint.local
    if (angle == 2 * Pi) // horizontally to right
      globalPoint.translate(
        - local.x + TileSize,
        0
      )
    else if (angle == Pi) // horizontally to left
      globalPoint.translate(
        - local.x,
        0
      )
    else if (angle == 3 * Pi / 2) // vertically to bottom
      globalPoint.translate(
        0,
        -local.y + TileSize
      )
    else if (angle == Pi / 2) // vertically to top
      globalPoint.translate(
        0,
        -local.y
      )
    else if (isTop)
      globalPoint.translate(
        round(local.y / tan(angle)).toInt,
        -local.y
      )
    else if (isBottom)
      globalPoint.translate(
        - round((TileSize - local.y) / tan(angle)).toInt,
        - local.y + TileSize
      )
    else if (isLeft)
      globalPoint.translate(
        - local.x,
        round(local.x / tan(angle)).toInt
      )
    else
      globalPoint.translate(
        - local.x + TileSize,
        - round((TileSize - local.x) / tan(angle)).toInt
      )
  }
}

case class Quadrant(corner: TileCorner, angle: Double) {
  def nextLocation(globalPoint: Point, angle: Double) =
    corner.nextLocation(globalPoint, angle)

  def nextTileOffset(normalizedAngle: Double) =
    if (normalizedAngle != angle)
      corner.nextTileOffset
    else
      corner.cornerOffset
}

object Labyrinth {
  val TileCorners = Seq(
    TileCorner(Point(0, 0), IntegerPoint(0, -1), IntegerPoint(-1, -1)),
    TileCorner(Point(100, 0), IntegerPoint(1, 0), IntegerPoint(1, -1)),
    TileCorner(Point(100, 100), IntegerPoint(0, 1), IntegerPoint(1, 1)),
    TileCorner(Point(0, 100), IntegerPoint(-1, 0), IntegerPoint(-1, 1)),
    TileCorner(Point(0, 0), IntegerPoint(0, -1), IntegerPoint(-1, -1))
  )

  def apply(resourcePath: String = "default.lab"): Labyrinth =
    Source
      .fromInputStream(getClass.getClassLoader.getResourceAsStream(resourcePath))
      .getLines()
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
          playerStartingTile = if (playerColumn >= 0) Point(playerColumn, rIndex) else laby.playerStartingTile,
          objectiveTile = if (objectiveColumn >= 0) Point(objectiveColumn, rIndex) else laby.objectiveTile
        )
      }
}
