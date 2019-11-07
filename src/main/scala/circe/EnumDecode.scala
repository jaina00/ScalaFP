package circe

import io.circe.{Decoder, _}
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.Encoder
//import io.circe.generic.auto._
import io.circe.syntax._


object Gender extends Enumeration {
  type Gender = Value
  val Male, Female, Unisex, Unknown = Value
}

sealed trait Product{
  val id: String
  val gender: Gender.Value
}


case class Apple(id: String) extends Product {
  override val gender: Gender.Value = Gender.Male
}

object Abc extends App{

  sealed trait Thing{
    val id:String
    val gender: Gender.Value
  }
  case class SomeThing(id:String) extends Thing {
    override val gender: Gender.Value = Gender.Male
  }
  case class OtherThing(id:String) extends Thing {
    override val gender: Gender.Value = Gender.Male
  }

  import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  // serialize Thing to json
  implicit val hhh = Decoder.enumDecoder(Gender)
  val jsString: String = """{
     "SomeThing" : {
      "id" : "abc",
      "gender": "Male"
     }
  }"""

  // deserialize json to Thing
  val errorOrMyTrait: Either[io.circe.Error, Thing] = for {
    json <- parse(jsString) // either ADT, since this may not be a legal json
    myDecodedClass <- json.as[Thing] // ADT, since this may be a json that is not in Thing Format
  } yield myDecodedClass

  println(errorOrMyTrait.right.get.gender)
}

object AAA extends App{
  implicit val printer = Printer.noSpaces.copy(dropNullValues = true)
  case class User(id: Long, firstName: String, lastName: String, age: Option[Int])
  implicit val encodeUser: Encoder[User] =
    Encoder.forProduct4("id", "first_name", "last_name", "age")(u =>
      (u.id, u.firstName, u.lastName, u.age))


  implicit val encodeUser2: Encoder[User] = deriveEncoder


  val user = User(1, "a","b", None)

  //println(user.asJson.toString())

}