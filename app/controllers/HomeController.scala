package controllers

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import javax.inject._
import play.api._
import play.api.mvc._

import utils.auth.Session

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  // show are tokens available..
  def getAllTokens() = Action.async { implicit request =>
    Future.successful(Ok(Session.loginToken.toString))
  }
  // generate sample token on the id
  def createToken() = Action.async { implicit request =>
    Session
      .loginToken
      .addOne(UUID.fromString("338e203a-1321-491e-b54c-b5797c503af9") -> Session.generateToken())
    Future.successful(Ok(Session.loginToken.toString))
  }
}
