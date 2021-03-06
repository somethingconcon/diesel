package readme

import diesel.ktrans
import cats._
import diesel.implicits._

object KTransDemo {

  @ktrans
  trait Maths[G[_]] {
    def add(l: Int, r: Int): G[Int]
    def subtract(l: Int, r: Int): G[Int]
    def times(l: Int, r: Int): G[Int]
  }

  val MathsIdInterp = new Maths[Id] {
    def add(l: Int, r: Int)      = l + r
    def subtract(l: Int, r: Int) = l - r
    def times(l: Int, r: Int)    = l * r
  }

  val idToOpt = λ[Id ~> Option](Some(_))

  // use the auto-generated transformK method to create a Maths[Option] from Maths[Id]
  val MathsOptInterp = MathsIdInterp.transformK(idToOpt)

  assert(MathsOptInterp.add(3, 10) == Some(13))

}
