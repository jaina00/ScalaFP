package scalaCheck

import java.time.Instant

import org.scalacheck.Gen

object CreateRandomCaseClass extends App {

  case class MyHotel(price: BigDecimal, starRating: BigDecimal, distanceToCityCenter: Option[BigDecimal], distanceToStation: Option[BigDecimal])

  val randomHotel: Gen[MyHotel] = for {
    price <- Gen.choose(50d, 2000d).map(BigDecimal.valueOf)
    star <- Gen.choose[Int](1, 5)
    distanceToCityCenter: Option[BigDecimal] <- Gen.option(Gen.choose(0.25d, 50d).map(BigDecimal.valueOf))
    distanceToStation: Option[BigDecimal] <- Gen.option(Gen.choose(0.25d, 50d).map(BigDecimal.valueOf))
  } yield MyHotel(price, star, distanceToCityCenter, distanceToStation)

  val listOfListOfHotels = Gen.listOfN(1, Gen.listOfN(100, randomHotel)).sample.get

  println(listOfListOfHotels.flatten.size)

  val stTime1 = Instant.now()

  listOfListOfHotels.foreach { hotels =>
    hotels.foreach {
      var minStar: BigDecimal = Int.MaxValue
      var maxStar: BigDecimal = 0
      var minPrice: BigDecimal = Int.MaxValue
      var maxPrice: BigDecimal = 0
      var minDistanceToStation: BigDecimal = Int.MaxValue
      var maxDistanceToStation: BigDecimal = 0
      var minDistanceToCityCenter: BigDecimal = Int.MaxValue
      var maxDistanceToCityCenter: BigDecimal = 0

      htl =>
        if (htl.starRating < minStar)
          minStar = htl.starRating
        if (htl.starRating > maxStar)
          maxStar = htl.starRating
        if (htl.price < minPrice)
          minPrice = htl.price
        if (htl.price > maxPrice)
          maxPrice = htl.price
        if (htl.distanceToStation.isDefined && htl.distanceToStation.get < minDistanceToStation)
          minDistanceToStation = htl.distanceToStation.get
        if (htl.distanceToStation.isDefined && htl.distanceToStation.get > maxDistanceToStation)
          maxDistanceToStation = htl.distanceToStation.get
        if (htl.distanceToCityCenter.isDefined && htl.distanceToCityCenter.get < minDistanceToCityCenter)
          minDistanceToCityCenter = htl.distanceToCityCenter.get
        if (htl.distanceToCityCenter.isDefined && htl.distanceToCityCenter.get > maxDistanceToCityCenter)
          maxDistanceToCityCenter = htl.distanceToCityCenter.get
    }
  }


  val g: BigDecimal = -5.2

  println(g.abs)
  println(g < 0)

  println(Instant.now().toEpochMilli - stTime1.toEpochMilli)

  val stTime2 = Instant.now()
  listOfListOfHotels.foreach { hotels =>
    val x = hotels.map(_.starRating)
    val y = hotels.map(_.price)
    val a = hotels.flatMap(_.distanceToStation)
    val b = hotels.flatMap(_.distanceToCityCenter)
    x.min
    x.max
    y.min
    y.max
    a.min
    a.max
    b.min
    b.max
  }
  //  println("minStar > " + x.min + " maxStar > " + x.max + " minPrice > " + y.min + " maxPrice > " + y.max)
  //  println("minStation > " + a.min + " maxStation > " + a.max + " minCityCenter > " + b.min + " maxCityCenter > " + b.max)

  println(Instant.now().toEpochMilli - stTime2.toEpochMilli)

}