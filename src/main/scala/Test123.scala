import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.pattern.pipe
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class HelloActor1 extends Actor {
  def receive = {
    case "hello1" =>
      val senderRef = sender()
      val f = Future {
        println("111222")
        throw new Exception("1111")
      }

      f.onComplete {
        case Success(ex: Throwable) => println("success")
        case Failure(exception) => println("failed")
          senderRef ! Failure(exception)//akka.actor.Status.Failure(exception)
      }
  }
}

class HelloActor2 extends Actor {
  implicit val timeout = Timeout.apply(10, TimeUnit.SECONDS)

  val system = ActorSystem("HelloSystem")
  val helloActor1 = system.actorOf(Props[HelloActor1], name = "helloactor1")

  def receive = {
    case "hello2" =>
      val senderRef = sender()
      (helloActor1 ? "hello1") pipeTo senderRef
  }
}

object Test123 extends App {
  implicit val timeout = Timeout.apply(10, TimeUnit.SECONDS)
  val system = ActorSystem("HelloSystem")
  val helloActor2 = system.actorOf(Props[HelloActor2], name = "helloactor2")

  val f = (helloActor2 ? "hello2").onComplete {
    case Success(_: String) => println("success string on complete")
    case Success(_) => println("success on dddd")
    case Success(ex: Throwable) => println("success on complete")
    case Failure(ex: Throwable) =>
      ex.printStackTrace()
      println("failure on complete")
  }
  //Await.ready(f, Duration.Inf)
}
