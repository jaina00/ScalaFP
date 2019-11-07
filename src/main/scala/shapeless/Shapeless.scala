import PolyMap.size.at
import circe.Gender


case class Product(id: String, gender: Gender.Value)

object PolymorphicExamples extends App {
  import shapeless.poly._

  //The below code is natural transformation to convert Set to Option
  val setToOptionNatTrans = new (Set ~> Option) {
    def apply[T](s: Set[T]) = s.headOption
  }

  println(setToOptionNatTrans(Set(1, 2, 3))) //Some(1)
  println(setToOptionNatTrans(Set('a', 'b', 'c'))) //Some('a')

  //Wtih Shapeless we can pass these transformers to functions
  def pairApply(f: Set ~> Option) = (f(Set(1, 2, 3)), f(Set('a', 'b', 'c')))
  println(pairApply(setToOptionNatTrans))
}

object PolyMap extends App {
  import shapeless.HList._
  import shapeless._

  object size extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseProduct = at[Product](x => x.id)
    implicit def caseString = at[String](_.length)
  }


  object gg extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseProduct = at[Product](x => x.id)
    implicit def caseString = at[String](_.length)
  }

  val sets = 1 :: "foofasdfasdfasdf" :: Product("1233352345345", Gender.Male) :: HNil

  //println(sets.map(size))
  val poly = gg
  println(sets.map(poly))

//  println(size(23))
//  println(size("foo"))
//  println(size(Product("123", Gender.Male)))

}