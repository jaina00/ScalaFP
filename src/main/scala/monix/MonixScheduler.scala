//package monix
//
//import monix.execution.Cancelable
//
//import scala.concurrent.Future
//import scala.concurrent.ExecutionContext.Implicits.global
//
//object MonixScheduler extends App {
//
//  import monix.execution.Scheduler.{global => scheduler}
//
//  import scala.concurrent.duration._
//
//  var x = 0
//  var y = 0
//
//  def myFuture: Future[Unit] = Future{
//    Thread.sleep(5000)
//    x = x +1
//    println("hello future" + (x))
//
//    throw new Exception("error")
//  }
//
//
//  val c: Cancelable = scheduler.scheduleWithFixedDelay(0.seconds, 1.seconds) {
//    myFuture.timeoutTo(3.seconds, Future.failed(new Exception("hello timeout"))).recover{
//      case ex =>
//        y =y +1
//        println(ex.getMessage + "--"+y)
//    }
//    println("11111")
//  }
//
//  Thread.sleep(10000)
//}
