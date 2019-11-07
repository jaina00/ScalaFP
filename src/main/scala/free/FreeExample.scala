package free

import cats.free.Free
import cats.free.Free.liftF
import cats.arrow.FunctionK
import cats.{Id, ~>}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

sealed trait KVStoreA[A]
case class Put[T](key: String, value: T) extends KVStoreA[Unit]
case class Get[T](key: String) extends KVStoreA[Option[T]]
case class Delete(key: String) extends KVStoreA[Unit]
object FreeExample extends App{
  type KVStore[A] = Free[KVStoreA, A]

  def put[T](key: String, value: T): KVStore[Unit] =
    liftF[KVStoreA, Unit](Put[T](key, value))

  // Get returns a T value.
  def get[T](key: String): KVStore[Option[T]] =
    liftF[KVStoreA, Option[T]](Get[T](key))

  // Delete returns nothing (i.e. Unit).
  def delete(key: String): KVStore[Unit] =
    liftF(Delete(key))

  // Update composes get and set, and returns nothing.
  def update[T](key: String, f: T => T): KVStore[Unit] =
    for {
      vMaybe <- get[T](key)
      _ <- vMaybe.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
    } yield ()


  def impureCompiler: KVStoreA ~> Id  =
    new (KVStoreA ~> Id) {

      // a very simple (and imprecise) key-value store
      val kvs = mutable.Map.empty[String, Any]

      def apply[A](fa: KVStoreA[A]): Id[A] =
        fa match {
          case Put(key, value) =>
            println(s"put($key, $value)")
            kvs(key) = value
            ()
          case Get(key) =>
            println(s"get($key)")
            //kvs.get(key).map(_.asInstanceOf[A])
            throw new Exception("hello")
          case Delete(key) =>
            println(s"delete($key)")
            kvs.remove(key)
            ()
        }
    }

  val ff = Some(100)

  def program = Try {
    val g: Free[KVStoreA, Option[Int]] = (for {
      _ <- put("wild-cats", 2)
      _ <- update[Int]("wild-cats", (_ + 12))
      _ <- put("tame-cats", 5)
      n <- get[Int]("wild-cats")
      _ <- delete("tame-cats")
    } yield n)

      g.foldMap(impureCompiler)
  }

  val t = program match{
    case Success(x) => x
    case Failure(exception) => println("xxxxx")
      //throw exception
  }

  //t.foldMap(impureCompiler)
}
