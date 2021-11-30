package utils.auth

import java.time.Instant
import java.util.UUID
import scala.collection.mutable.HashMap

object Session {
  // 5 minutes default token expiration
  val expiration: Int = (5 * 60)
  // account id ~> [token, Expiration time]
  val loginToken = HashMap.empty[UUID, (String, Long)]

  def generateToken(): (String, Long) = (s"==token${UUID.randomUUID().toString}", Instant.now.getEpochSecond + expiration)
}
