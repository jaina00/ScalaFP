package shapeless;

object ShapelessUnion extends App {

  type ISB = List[Int] :+: List[String] :+: CNil

  case class Hotel(id: String, rooms: ISB)


  val isb = Coproduct[ISB](List(123))

  val g: Option[List[Int]] = isb.select[List[Int]]

  println(g)

  val fff = Coproduct[ISB](List(1, 2, 3))
  val gg = Hotel("111", fff)

  println(gg)


  def printHotel(hotel: Hotel): Unit ={
    println(hotel.id)
    println(hotel.rooms)
  }

  printHotel(gg)

}
