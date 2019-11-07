import java.io.PrintWriter
import scala.reflect.io.File
import java.io._

object LTAReportApp11 extends App {
  val originalFormat: DateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd")

  val g = Util.hotelAndTrainFeed(Days(1), originalFormat.parseDateTime("20190930"))

  var s = 0
  var d = 100
  g._1.map(x => {
    import java.io.PrintWriter
    new PrintWriter("/hotel/" + s.toString + ".txt") {
      write(x.feed);
      close
    }
    s = s + 1
  })

  g._2.map(x => {
    import java.io.PrintWriter
    new PrintWriter("/train/" + d.toString + ".txt") {
      write(x.feed);
      close
    }
    d = d + 1
  })

}
