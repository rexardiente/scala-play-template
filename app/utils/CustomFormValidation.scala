package utils

import java.time.Instant
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formatter
import play.api.data.format.Formats._

class CustomFormValidation extends Formatter[Instant] {
  import play.api.data.FormError
  override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Instant] = try {
    Right(Instant.parse(data(key)))
  } catch{
    case _:Throwable => Left(Seq(new FormError(key, Seq("error.date"))))
  }
  override def unbind(key: String, value: Instant): Map[String, String] = Map(key -> value.toString)

  def of[Instant](implicit binder: Formatter[Instant]) = play.api.data.FieldMapping[Instant]()(binder)
}

