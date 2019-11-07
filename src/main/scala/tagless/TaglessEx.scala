package tagless
import cats._
import cats.implicits._


trait KVStore[F[_]] {
  def get(key: String): F[Option[String]]
  def put(key: String, a: String): F[Unit]
}

object TaglessEx {
  def program[M[_]: FlatMap, F[_]](a: String)(K: KVStore[M])(implicit P: Parallel[M, F]): M[Option[String]] =
    for {
      _ <- K.put("A", a)
      x <- (K.get("B"), K.get("C")).parMapN(_ |+| _)
      _ <- K.put("X", x.getOrElse("-"))
    } yield x


}
