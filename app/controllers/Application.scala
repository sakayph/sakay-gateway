/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package controllers

import java.sql.Timestamp

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.libs.json._

import play.api.data._
import play.api.data.Forms._

import sms._
import models._
import database.{Messages,Searches,Pending}

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

  def sms = SecureAction { implicit request =>
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

  val logForm = Form(
    mapping(
      "fromName" -> text,
      "fromLat" -> bigDecimal,
      "fromLng" -> bigDecimal,
      "toName" -> text,
      "toLat" -> bigDecimal,
      "toLng" -> bigDecimal
    ) {
      case (fromName, fromLat, fromLng, toName, toLat, toLng) =>
        Search("web", fromName, Some(LatLng(fromLat.doubleValue, fromLng.doubleValue)), toName, Some(LatLng(toLat.doubleValue, toLng.doubleValue)))
    }
    {
      case _ => None
    }
  )

  def log = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    logForm.bindFromRequest.fold(
      errors => withCors(BadRequest("incomplete")),
      { search =>
        Searches.save(search.copy(source = request.headers.get("Referer").getOrElse("web")))
        withCors(Ok("ok"))
      }
    )
  }

  val sendForm = Form(
    tuple(
      "target" -> text,
      "itinerary" -> text
    )
  )

  def send = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    sendForm.bindFromRequest.fold(
      errors => withCors(BadRequest("incomplete")),
      { case (target, itinerary) =>
        RouteHandler.formatItinerary(Json.parse(itinerary)) match {
          case Some(message) =>
            Pending.save(target, message)
            withCors(Ok("ok"))

          case None =>
            withCors(BadRequest("empty"))
        }
      }
    )
  }

  def poll = SecureAction { request =>
    Ok(Json.toJson(Pending.list()))
  }

  def allowedOrigin = Play.mode match {
    case Mode.Dev => "*"
    case _ => "http://sakay.ph"
  }

  def withCors(result: Result) = result.withHeaders(
    "Access-Control-Allow-Origin" -> allowedOrigin
  )

  def cors = Action {
    Ok("").withHeaders(
      "Access-Control-Allow-Origin" -> allowedOrigin,
      "Access-Control-Allow-Headers" -> "X-Requested-With",
      "Access-Control-Allow-Methods" -> "GET, POST, OPTIONS"
    )
  }
}
