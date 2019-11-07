//http://allaboutscala.com/tutorials/chapter-9-beginner-tutorial-using-scala-futures/#future-traverse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object FutureVsTraversal extends App {
  def donutStock(donut: String): Future[Option[Int]] = Future {
    println("checking donut stock ... sleep for 2 seconds")
    //Thread.sleep(2000)
    if(donut == "vanilla donut") Some(10) else None
  }

  def buyDonuts(quantity: Int): Future[Boolean] = Future {
    println(s"buying $quantity donuts ... sleep for 3 seconds")
    Thread.sleep(3000)
    if(quantity > 0) true else false
  }

  def processPayment(): Future[Unit] = Future {
    println("processPayment ... sleep for 1 second")
    Thread.sleep(1000)
  }

//  val futureOperations: List[Future[Any]] = List(donutStock("vanilla donut"), buyDonuts(10), processPayment())
//
//  val futureSequenceResults: Future[List[Any]] = Future.sequence(futureOperations)
//  futureSequenceResults.onComplete {
//    case Success(results) => println(s"Results $results")
//    case Failure(e)       => println(s"Error processing future operations, error = ${e.getMessage}")
//  }

  val futureOperationsTrav: Seq[Future[Any]] = List(
    donutStock("vanilla donut"),
    donutStock("plain donut"),
    donutStock("chocolate donut"),
    Future.failed(new Exception("jjjj"))
  )

  val futureTraverseResult: Future[Seq[String]] = Future.traverse(futureOperationsTrav){ futureSomeQty: Future[Any] =>
    futureSomeQty.map(someQty => {
      println(">>>>>" + someQty.toString)
      someQty.toString
    })
  }

  println(Await.result(futureTraverseResult, Duration.Inf))
}
