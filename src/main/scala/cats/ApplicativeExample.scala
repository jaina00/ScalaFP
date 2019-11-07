package cats

import cats.free.FreeApplicative.{FA, lift}
import cats.implicits._
import cats.~>

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
sealed trait MyAction[A]

case class Person(fName: String)
case class Address(fLine: String)

case class GetPerson(fName: String)  extends MyAction[Person]
case class GetAddress(fLine: String) extends MyAction[Address]

object ApplicativeExample extends App{
  type MyActionFA[A] = FA[MyAction, A]

  def personApp(fName:String): MyActionFA[Person] = lift(GetPerson(fName))
  def addressApp(fLine:String): MyActionFA[Address] = lift(GetAddress(fLine))

  import cats.instances.future._

  def compute(fName:String, fLine:String ): MyActionFA[(Person, Address)] = {
    (
      personApp(fName),
      addressApp(fLine)
    ).mapN{ case (p: Person, a: Address) =>
      (p,a)
    }
  }


  val interpretWithFuture: MyAction ~> Future = {
    λ[MyAction ~> Future] {
      case GetPerson(fName: String) =>
        Future{
          println("GetPerson")
          //Thread.sleep(5000)
          throw new Exception("oops")
        }
      case GetAddress(fLine: String)=>
        Future{
          println("GetAddress")
          Address("churchill")
        }
    }
  }

  val g = compute("dddd","eeeee").foldMap(interpretWithFuture)

  val r = g.map{ res =>
    println("res >>>>>")
  }

  Await.result(r, 20 seconds)

}
