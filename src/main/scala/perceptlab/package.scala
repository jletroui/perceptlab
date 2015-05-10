import java.util.function.UnaryOperator

package object perceptlab {

  def using[C <: { def close() }, A](resource: C)(action: C => A): A =
    try { action(resource) }
    finally { resource.close() }

  implicit def toUnaryOperator[T](action: T => T) = new UnaryOperator[T] {
    override def apply(t: T): T =
      action(t)
  }
}
