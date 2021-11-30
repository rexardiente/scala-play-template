package models.domain

import java.util.UUID
import java.time.Instant
import play.api.libs.json.{ Json, JsValue }
import utils.{ CommonImplicits, RegexPatterns }

object UserAccountInfo extends CommonImplicits {
	val tupled = (apply: (UUID, String, String, String, Instant) => UserAccountInfo).tupled
	def apply(id: UUID, username: String, password: String, email: String, createdAt: Instant): UserAccountInfo =
		new UserAccountInfo(id, username, password, email, createdAt)
	// create new apply method without defining id and createdAt field..
	def apply(username: String, password: String, email: String): UserAccountInfo =
		new UserAccountInfo(UUID.randomUUID, username, password, email, Instant.now())
}

case class UserAccountInfo(id: UUID, username: String, password: String, email: String, createdAt: Instant) {
	require(RegexPatterns.email.pattern.matcher(email).matches(), "invalid email address")
	def toJson(): JsValue = Json.toJson(this)
}
