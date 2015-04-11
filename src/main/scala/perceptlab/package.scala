package object perceptlab {

  def using[C <: { def close() }, A](resource: C)(action: C => A): A =
    try { action(resource) }
    finally { resource.close() }

}
