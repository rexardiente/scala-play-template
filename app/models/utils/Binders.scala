package utils

import java.time.Instant
import play.api.mvc.{ PathBindable, QueryStringBindable }

object Binders {
  implicit def queryStringInstantBinder(implicit  instantBinder: QueryStringBindable[String] ) = new QueryStringBindable[Instant] {
    override def bind(key: String, value: Map[String, Seq[String]]): Option[Either[String, Instant]] = {
      try {
        for {
          param   <- instantBinder.bind(key, value)
        } yield {
          (param) match {
            case (Right(param)) => Right(Instant.parse(param))
            case _ => Left("Invalid Time Format")
          }
        }
      } catch {
        case _:Throwable => Some(Left("Invalid Time Format"))
      }
    }

    override def unbind(key: String, value: Instant): String =
      instantBinder.unbind(key, value.toString)
  }

  implicit def pathBindLongToUnix2(implicit intBinder: PathBindable[String]) = new PathBindable[Instant] {
    override def bind(key: String, value: String): Either[String, Instant] = {
      for {
        index <- intBinder.bind(key, value)
        convert <- try {
                    // split by `=` just in case user provides key and value
                    Right(Instant.parse(index.split("=").last))
                  } catch {
                    case e: Throwable => Left("Invalid Time Format")
                  }
      } yield convert
    }
    override def unbind(key: String, value: Instant): String = value.getEpochSecond.toString
  }
}