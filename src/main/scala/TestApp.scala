//package phone.com
//
//import cats.data.ValidatedNel
//import cats.implicits._
//import phone.com.models._
//import phone.com.utils.Constants._
//
//import scala.io.Source
//
//object Check extends App {
//
//  type ValidationResult[A] = ValidatedNel[CallDataError, A]
//
//  val input = Source.fromResource(InputFile).getLines.toVector
//
//  val a: ValidatedNel[CustomerIdValidationError.type, CustomerId] = CustomerId("A").toValidNel(CustomerIdValidationError)
//  val b: ValidatedNel[PhoneNumberCalledValidationError.type, PhoneNumber] = PhoneNumber("555-634-952").toValidNel(PhoneNumberCalledValidationError)
//
//
//  def validateCall(singleCall: Array[String]): ValidationResult[Call] =  (
//    CustomerId(singleCall(0)).toValidNel(CustomerIdValidationError),
//    PhoneNumber(singleCall(1)).toValidNel(PhoneNumberCalledValidationError),
//    CallDuration(singleCall(2)).toValidNec(CallDurationValidationError)
//  ).mapN(Call(_,_,_))
//
//}
//
//sealed trait CallDataError
//
//final case object CustomerIdValidationError extends CallDataError
//
//final case object PhoneNumberCalledValidationError extends CallDataError
//
//final case object CallDurationValidationError extends CallDataError
//
//package phone.com.models
//
//case class Call(customerId: CustomerId,
//                phoneNumberCalled: PhoneNumber,
//                callDuration: CallDuration)
//
//object Call {
//  def apply(customerId: CustomerId,
//            phoneNumberCalled: PhoneNumber,
//            callDuration: CallDuration) = new Call(customerId, phoneNumberCalled, callDuration)
//}
//
//case class CallDuration(duration: String)
//
//object CallDuration {
//
//  def apply(duration: String): Option[CallDuration] =
//    Some(duration).filter(isValidCallDuration).map(new CallDuration(_))
//
//  def isValidCallDuration(duration: String): Boolean = duration match {
//    case null                  => false
//
//    case d if d.trim.isEmpty   => false
//
//    case d if {
//      val r = d.split(":")
//      r(0).matches("[0-2][0-3]") && r(1).matches("[0-5][0-9]") && r(2).matches("[0-5][0-9]")
//    }                          => true
//
//    case _                     => false
//  }
//}
//
