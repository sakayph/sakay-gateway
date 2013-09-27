package controllers

import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.Play.current

import models._

trait Secured {
  self: Controller =>

  val key = current.configuration.getString("sakay.key").get

  def SecureAction(f: Request[AnyContent] => Result): Action[AnyContent] = SecureAction(parse.anyContent)(f)

  def SecureAction[A](parser: BodyParser[A])(f: Request[A] => Result): Action[A] = Action(parser) { implicit req =>
    if(req.headers.get("X-API-KEY").map(_ == key).getOrElse(false)) {
      f(req)
    }
    else {
      NotFound("404")
    }
  }
}
