package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import sms._
import database.Messages

object Application extends Controller {

  val form = Form(
    of(SmsMessage.apply _, SmsMessage.unapply _)(
      "source" -> text,
      "body" -> text,
      "target" -> text
    )
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

}
