package controllers

import javax.inject.{ Inject, Singleton }
import java.util.UUID
import java.time.Instant
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.{ Constraint, ValidationError, Valid, Invalid }
import play.api.data.validation.Constraints.emailAddress
import play.api.libs.json._
import utils.auth.SecureUserAction
import utils.CustomFormValidation
import models.domain.UserAccountInfo
import models.service.UserAccountService

@Singleton
class SecureActionController @Inject()(
      accountService: UserAccountService,
      cc: ControllerComponents,
      SecureUserAction: SecureUserAction) extends AbstractController(cc) {

  private val uuidForm = Form(single("id" -> uuid))
  private val accInfoForm = Form(mapping(
    "id" -> default(uuid, UUID.randomUUID),
    "username" -> nonEmptyText,
    "password" -> nonEmptyText,
    "email" -> email.verifying(emailAddress),
    "created_at" -> default(of(new CustomFormValidation()), Instant.now)
  )(UserAccountInfo.apply)(UserAccountInfo.unapply))
  // Below are sample request with SecureAction,
  // change the stracture based on your needs..
  def getUserAccountInfo() = SecureUserAction.async { implicit request =>
    request.account.map { account =>
      accountService
        .findByID(account.id)
        .map(_.map(res => Ok(Json.toJson(res))).getOrElse(NotFound))
    }.getOrElse(Future(Unauthorized(views.html.defaultpages.unauthorized())))
  }

  def addUserAccountInfo() = SecureUserAction.async { implicit request =>
    request.account.map { account =>
      accInfoForm
        .bindFromRequest()
        .fold(formErr => Future.successful(BadRequest(formErr.toString)),
        { case info@UserAccountInfo(id, username, password, email, createdAt)  =>
          accountService
            .add(info)
            .map(x => if (x > 0) Created else InternalServerError)
        })
    }.getOrElse(Future(Unauthorized(views.html.defaultpages.unauthorized())))
  }
  // update account info execpt user account id
  def updateUserAccountInfo() = SecureUserAction.async { implicit request =>
    request.account.map { account =>
      accInfoForm
        .bindFromRequest()
        .fold(formErr => Future.successful(BadRequest(formErr.toString)),
        { case UserAccountInfo(id, username, password, email, createdAt)  =>
          accountService
            .update(new UserAccountInfo(account.id, username, password, email, createdAt))
            .map(x => if (x > 0) Accepted else NotFound)
        })
    }.getOrElse(Future(Unauthorized(views.html.defaultpages.unauthorized())))
  }

  def deleteUserAccountInfo() = SecureUserAction.async { implicit request =>
    request.account.map { account =>
      uuidForm
        .bindFromRequest()
        .fold(formErr => Future.successful(BadRequest(formErr.toString)),
        { case (id) =>
          accountService
            .delete(id)
            .map(x => if (x > 0) Accepted else NotFound)
        })
    }.getOrElse(Future(Unauthorized(views.html.defaultpages.unauthorized())))
  }
}