package models.utils

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data.format.Formatter
import play.api.data.format.Formats._
import models.domain._

trait CommonImplicits {
	// UserAccountInfo custom reads and writes walidations
	implicit val implicitUserAccountInfoReads: Reads[UserAccountInfo] = new Reads[UserAccountInfo] {
		override def reads(js: JsValue): JsResult[UserAccountInfo] = js match {
			case json: JsValue => {
				try {
					JsSuccess(UserAccountInfo(
						(json \ "id").as[UUID],
						(json \ "username").as[String],
						(json \ "password").as[String],
						(json \ "email").as[String],
						(json \ "created_at").as[Instant]))
				} catch {
					case e: Throwable => JsError(Seq(JsPath() -> Seq(JsonValidationError(e.toString))))
				}
			}
			case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.expected.jsobject"))))
		}
	}
	implicit val implicitUserAccountInfoWrites = new Writes[UserAccountInfo] {
	  def writes(v: UserAccountInfo): JsValue = Json.obj(
		"id" -> v.id,
		"username" -> v.username,
		"password" -> v.password,
		"email" -> v.email,
		"created_at" -> v.createdAt)
	}

}