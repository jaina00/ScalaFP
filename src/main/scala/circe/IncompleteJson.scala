package circe
import io.circe.generic.auto._
import io.circe.{Decoder, _}
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.Encoder
//import io.circe.generic.auto._
import io.circe.syntax._

object IncompleteJson extends App{
  case class Address(stName:String)
  case class Person(name: String, age: Int, address: Address)

  val person = Person("mr complete", 42, Address("street1"))
  val incompletePersonJson = """{"name":"mr updated"}"""
  val update = decode[Person => Person](incompletePersonJson)
  println(update.map(per => per(person)))
}
