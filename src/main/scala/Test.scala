//object Rate extends Enumeration {
//  val standalone, `package` = Value
//}
//
//object ProductType extends Enumeration {
//  val hotel, train = Value
//}
//
//trait PricingRequestProducts {
//  val id: Int
//  val `type`: ProductType.Value
//  val rate: Rate.Value
//  val region: RegionCode
//  val checkinDate: String
//  val checkoutDate: String
//  val market: String
//  val passengers: PricingPassenger
//}
//trait PricingResponseProducts
//
//trait PricingProductsIO {
//  type requestProduct <: PricingRequestProducts
//  type responseProducts <: PricingResponseProducts
//  type requestType
//  def requestProductsList(request: requestType): List[requestProduct]
//  val responseProductsList: List[responseProducts]
//}
//
//object PricingProductsIO {
//  implicit val gg = new PricingProductsIO {
//    override type requestProduct = HotelStandalonePricingProduct
//    override type responseProducts = Response
//    override val responseProductsList: List[Response] = List.empty
//    override type requestType = String
//  }
//}
//
//case class HotelStandalonePricingProduct(id: Int, region: RegionCode, `type`: ProductType.Value, rate: Rate.Value, checkinDate: String, checkoutDate: String, market: String, passengers: PricingPassenger, rooms: List[PricingRoom]) extends PricingRequestProducts {
//}
//
//case class Response() extends PricingResponseProducts
//case class ProductsPriceRequest1(cid: String, products: List[PricingRequestProducts])
//
//case class ProductsPriceRequest(cid: String, body: ProductsPriceBody)
//
//case class ProductsPriceBody(products: List[PricingProduct])
//
//case class RegionCode(from: Option[Int], to: Int)
//
//case class PricingPassenger(adults: Int, children: Int)
//
//case class PricingRoom(roomId: String, price: BigDecimal, groupId: Int)
//
//sealed trait PricingProduct {
//
//  val rooms: List[PricingRoom]
//}
