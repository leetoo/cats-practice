package parsing

import scala.collection.immutable.HashMap

object CirceEncoderDecoder extends App {

  import java.text.SimpleDateFormat
  import java.util.Date

  import io.circe.generic.extras._
  import io.circe.syntax._
  import io.circe.{Decoder, Encoder}

  val h: HashMap[String, String] = HashMap("1" -> "one")
  println(h.asJson.noSpaces)
  import scala.util.Try

  @ConfiguredJsonCodec case class Bar(
                      @JsonKey("my-int") i: Int,
                      s: String,
                      d: Date
  )

  object Bar {
    private[this] def fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

    implicit val config: Configuration = Configuration.default
    implicit val dateEncoder: Encoder[Date] =
      Encoder[String].contramap(fmt.format)
    implicit val dateDecoder: Decoder[Date] =
      Decoder[String].emapTry(str => Try(fmt.parse(str)))
  }

}
