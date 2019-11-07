//package free
//
//import cats.arrow.FunctionK
//import cats.data.EitherK
//import cats.free.Free.liftF
//import cats.free.FreeApplicative.FA
//import cats.free.{Free, FreeApplicative}
//import cats.{Comonad, Id, Monad, Parallel, ~>}
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.Future
//
//sealed trait DSLAction[A]
//
//case class GetCustomer(request: Boolean) extends DSLAction[String]
//
//case class GetSize(request: Boolean) extends DSLAction[Int]
//
//object CombineFreeWithFreeAp extends App {
//  //  import cats.Eval
//  //  import cats.implicits._
//  //  val nested: Eval[Eval[Int]] = Eval.now(Eval.now(3))
//  //  val flattened: Eval[Int] = nested.flatten
//  //
//  //  type GitHubApplicative[A] = FreeApplicative[DSLAction, A]
//  //  type GitHubMonadic[A] = Free[DSLAction, A]
//  //  type GitHubBoth[A] = Free[EitherK[DSLAction,GitHubApplicative,?],A]
//  //
//  //
//  //  trait KVStore[F[_]] {
//  //    def get(key: String): F[Option[String]]
//  //    def put(key: String, a: String): F[Unit]
//  //  }
//  //
//  //  import cats._
//  //  import cats.implicits._
//  //
//  //  type Program[F[_], A] = Free[FreeApplicative[F, ?], A]
//  //
//  //  import cats.free.Free
//  //  import cats.implicits._
//  //  import cats.~>
//  //
//  //  val g: Free[DSLAction, (String, Int)] =
//  //    for {
//  //      x <- (liftF(GetTrainsFromProvider1(true)), liftF(GetTrainsFromProvider2(true))).mapN{ case (l, r) => (l, r)}
//  //    } yield x
//  //
//
//  val interpreter: DSLAction ~> Id = {
//    λ[DSLAction ~> Id] {
//      case GetCustomer(_: Boolean) => {
//        "hello"
//      }
//      case GetSize(_: Boolean) => {
//        123
//      }
//    }
//  }
//
//  val f1: Free[DSLAction, String] = liftF(GetCustomer(true))
//  val f2: Free[DSLAction, Int] = liftF(GetSize(true))
//  val f3: FA[DSLAction, Int] = FreeApplicative.lift(GetSize(true))
//  f3.monad
//
//  //  type FEF[A] = FreeApplicative[DSLAction, A]
//  //  implicit val sd: Monad[FEF] = new Monad[FEF] {
//  //    override def pure[A](x: A): FEF[A] = ???
//  //
//  //    override def flatMap[A, B](fa: FEF[A])(f: A => FEF[B]): FEF[B] = ???
//  //
//  //    override def tailRecM[A, B](a: A)(f: A => FEF[Either[A, B]]): FEF[B] = ???
//  //  }
//  //
//  //  def toApp[F,A](fe: Free[F, A]) =
//  //    fe.foldMap[FreeApplicative[F, ?]] {
//  //      λ[FunctionK[F, FreeApplicative[F, ?]]](fa => FreeApplicative.lift(fa))
//  //    }
//
//  type FEF[A] = FreeApplicative[DSLAction, A]
//  implicit val ggggg = new Monad[FEF] {
//
//    override def pure[A](x: A): FEF[A] = FreeApplicative.pure[DSLAction, A](x)
//
//    override def flatMap[A, B](fa: FEF[A])(f: A => FEF[B]): FEF[B] = ???
//
//    override def tailRecM[A, B](a: A)(f: A => FEF[Either[A, B]]): FEF[B] = {
//      val gggg: Id[Either[A, B]] = f(a).foldMap[Id] {
//        λ[FunctionK[DSLAction, Id]](fa => fa)
//      }
//
//      val x = FreeApplicative.pure[DSLAction, B](gggg.right.get)
//      x
//    }
//  }
//
//  import cats.free.FreeApplicative
//  import cats.free.FreeApplicative.lift
//  import cats.implicits._
//  import cats.~>
//
//  final def toApplicative[F, A](free: Free[DSLAction, A]) =
//    free.foldMap[FreeApplicative[DSLAction, ?]] {
//      λ[FunctionK[DSLAction, FEF]](fa => FreeApplicative.lift(fa))
//    }
//
//  val f1AP: FEF[String] = toApplicative(f1)
//  val f2AP: FEF[Int] = toApplicative(f2)
//
//
//  val prog = for {
//    a <- (f1AP, f2AP).mapN { case (l, r) => l + r }.monad
//    b <- f3
//  } yield {
//    (a, b)
//  }
//
//  prog.foldMap(interpreter)
//
//
//}