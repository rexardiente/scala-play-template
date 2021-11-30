package utils.auth

import javax.inject.{ Inject, Singleton }
import java.time.Instant
import java.util.UUID
import scala.concurrent.{ ExecutionContext, Future }
import play.api.mvc._
import utils.auth.Session
import models.domain.UserAccountInfo
import models.service.{ UserAccountService }

@Singleton
class SecureUserRequest[A](val account: Option[UserAccountInfo], request: Request[A]) extends WrappedRequest[A](request)

@Singleton
class SecureUserAction@Inject()(
      userAccService: UserAccountService,
      val parser: BodyParsers.Default,
      implicit val executionContext: ExecutionContext
    ) extends ActionBuilder[SecureUserRequest, AnyContent] with ActionTransformer[Request, SecureUserRequest] {
  def transform[A](request: Request[A]): Future[SecureUserRequest[A]] = {
    val id: UUID = request.headers.get("CLIENT_ID").map(UUID.fromString(_)).getOrElse(null)
    val token: String = request.headers.get("CLIENT_TOKEN").getOrElse(null)
    // session validation process..
    Session
      .loginToken
      .find(x => x._1 == id && x._2._1 == token)
      .map { session =>
        val now: Long = Instant.now().getEpochSecond
        // check if session time is valid
        if (session._2._2 >= now) {
          // check if account exists in DB
          userAccService.findByID(id).map { account =>
            // update existing login session by adding current time with expiration limit
            // return account info
            Session.loginToken(session._1) = (session._2._1, (now + Session.expiration))
            new SecureUserRequest(account, request)
          }
        } else {
          // else, remove id from the current session list and return nothing..
          Session.loginToken.remove(session._1)
          Future.successful(new SecureUserRequest(None, request))
        }
      }
      .getOrElse(Future.successful(new SecureUserRequest(None, request)))
  }
}