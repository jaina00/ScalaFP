package typeSystem

object KindProjectors extends App {

  trait Functor[F[_]]
  //1. The below type are fine as Option and List expects only 1 type
  type F1 = Functor[Option] // OK
  type F2 = Functor[List]   // OK

  //2. This will not work as Map takes 2 type parameters. But it got only one
  //type F3 = Functor[Map]    // Map takes two type parameters, expected: one


  //We can fix this using
  type IntKeyMap[A] = Map[Int, A]
  type F3 = Functor[IntKeyMap] // OK


  //3. We can still create F3 without creating this intermediate type
  type F5 = Functor[({ type T[A] = Map[Int, A] })#T] // OK

  //4. Or we can use kind projectors
  type F6 = Functor[Map[Int, ?]]
  //and pass it like

  def foo[A[_, _], B](functor: Functor[A[B, ?]]) = ???
  val g: Option[Map[Int, String]] = Option(Map(1 -> "abc"))
  //foo(g)
}
