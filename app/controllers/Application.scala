package controllers

import java.sql.Timestamp

import play.api._
import play.api.mvc._
import play.api.libs.json._

import play.api.data._
import play.api.data.Forms._

import sms._
import database.Messages

object Application extends Controller with Secured {

  val form = Form(
    mapping(
      "source" -> text,
      "body" -> text,
      "target" -> text
    ) {
      case (source, body, target) =>
        SmsMessage(new Timestamp(System.currentTimeMillis), source, body, target)
    }
    {
      case _ => None
    }
  )

  def sms = Action { implicit request =>
    form.bindFromRequest.fold(
      errors => BadRequest("incomplete"),
      { request =>
        val id = Messages.saveIncoming(request)
        val message = Handler.process(request)
        Messages.saveOutgoing(message, id)
        Ok(message.body)
      }
    )
  }

  def list(since: Option[Long]) = SecureAction { implicit request =>
    val list = Messages.list(since.getOrElse(System.currentTimeMillis)).map {
      case (message, reply) => Json.obj(
        "message" -> message,
        "reply" -> reply
      )
    }
    Ok(Json.toJson(list))
  }
}
