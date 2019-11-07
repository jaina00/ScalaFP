package typeSystem

object Franchise {
  case class Character(name: String)
}

class Franchise(name: String) {
  import Franchise.Character
  def createFanFiction(lovestruck: Character,objectOfDesire: Character): (Character, Character) = (lovestruck, objectOfDesire)
}

object PathDependentType extends App{

  val starTrek = new Franchise("Star Trek")
  val starWars = new Franchise("Star Wars")

  val quark = Franchise.Character("Quark")
  val jadzia = Franchise.Character("Jadzia Dax")

  val luke = Franchise.Character("Luke Skywalker")
  val yoda = Franchise.Character("Yoda")

  starTrek.createFanFiction(lovestruck = jadzia, objectOfDesire = luke)


  case class A(num:Int) {
    case class B(str: String)
    var b: Option[B] = None
  }
  val a1 = A(1)
  val a2 = A(2)
  val b1: a1.B = a1.B("hello")
  val b2: a2.B = a2.B("hello")
  a1.b = Some(b1)
  //a2.b = Some(b1)
}


class Franchise123(name123: String) {
  case class Character(name: String)
  def createFanFictionWith(
                            lovestruck: Character,
                            objectOfDesire: Character): (Character, Character) = (lovestruck, objectOfDesire)
}