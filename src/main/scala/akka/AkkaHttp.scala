//package akka
//
//package com.example
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.marshalling.Marshal
//import akka.http.scaladsl.model._
//import akka.stream.ActorMaterializer
//
//import scala.concurrent.Future
//import scala.util.{Failure, Success}
//final case class Application(jarUri: String, className: String, args: List[String])
//
//object ApplicationPoster extends App {
//
//  implicit val system = ActorSystem()
//  implicit val ec = system.dispatcher
//  implicit val materializer = ActorMaterializer()
//
//  val http = Http(system)
//
//  def postApplication(app: Application): Future[HttpResponse] =
//    Marshal(app).to[RequestEntity] flatMap { entity =>
//      val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:8080/apps", entity = entity)
//      request.
//      http.singleRequest(request)
//    }
//
//  val application = Application("file:///home/gebal/gebal/example.jar", "com.pawelgebal.Main", Nil)
//
//  postApplication(application) onComplete {
//    case Failure(ex) => System.out.println(s"Failed to post $application, reason: $ex")
//    case Success(response) => System.out.println(s"Server responded with $response")
//  }
//}