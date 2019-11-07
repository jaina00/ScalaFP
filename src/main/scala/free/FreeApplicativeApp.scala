package free

import cats.free.FreeApplicative
import cats.free.FreeApplicative.lift
import cats.implicits._
import cats.~>

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
sealed abstract class ValidationOp[A]
case class Size(size: Int) extends ValidationOp[Boolean]
case object HasNumber extends ValidationOp[Boolean]


object FreeApplicativeApp extends App{
  type Validation[A] = FreeApplicative[ValidationOp, A]

  def size(size: Int): Validation[Boolean] = lift(Size(size))
  val hasNumber: Validation[Boolean] = lift(HasNumber)

  val prog: Validation[Boolean] = (size(5), hasNumber).mapN { case (l, r) => l && r}

  //prog.analyze()

  val parCompiler: ValidationOp ~> Future = {
    //println("callll")
    λ[ValidationOp ~> Future] {
              case Size(size) => Future {
                Thread.sleep(1054)
                println("11111")
                true
              }
              case HasNumber => Future {
                Thread.sleep(1050)
                println("2222")
                true
              }
    }
  }

  val g = prog.foldMap(parCompiler)
  val r = g.map{ res =>
    println("res >>>>>")
  }

  Await.result(r, 20 seconds)

  //println(g("ssss"))
}
