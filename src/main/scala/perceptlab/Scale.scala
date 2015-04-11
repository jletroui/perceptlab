package perceptlab

/**
 * Crop and interpolate values to the given integer range.
 */
case class Scale(fromRange: (Double, Double), toRange: (Int, Int)) {
  val minFrom = fromRange._1
  val maxFrom = fromRange._2
  val rangeFrom = maxFrom - minFrom

  val minTo = toRange._1
  val maxTo = toRange._2
  val rangeTo = maxTo - minTo

  val factor = rangeTo / rangeFrom

  def scale(v: Double): Int =
    if (v <= minFrom)
      minTo
    else if (v >= maxFrom)
      maxTo
    else
      minTo + ((v - minFrom) * factor).toInt
}
