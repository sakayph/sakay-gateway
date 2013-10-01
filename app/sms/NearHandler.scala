/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

import ws.{Google,OpenTripPlanner}
import play.api.libs.json._

object NearHandler extends Handler {
  def keyword = "NEAR"
  def process(message: SmsMessage) = {
    val response = parseMessage(message.body) match {
      case None => Left("Incorrect format. Message should be NEAR <address>")
      case Some(point) => Right(Google.geocode(point))
    }

    val reply = response
    .right.flatMap {
      case Some(point) => Right(OpenTripPlanner.stopsNearPoint(point))
      case None => Left("Could not find the location. Please try again with a different spelling.")
    }
    .right.flatMap {
      case Some(result) => Right(formatResult(result.json))
      case None => Left("Sorry, an error has occurred.")
    }
    .merge

    message.reply(reply)
  }


  def parseMessage(body: String): Option[String] = {
    val cleaned = body.dropWhile(_ != ' ').toLowerCase.trim
    if (cleaned.length == 0) None else Some(cleaned)
  }

  def formatResult(json: JsValue): String = {
    val stops = (json \ "stops").asInstanceOf[JsArray].value
    val routes = stops.flatMap { stop =>
      (stop \ "routes").asInstanceOf[JsArray].value
    }
    .map { route =>
      (route \ "id").as[String]
    }
    .distinct

    routes.flatMap { routeId =>
      OpenTripPlanner.routeData(routeId)
    }
    .map(_.json.\("routeData")(0).\("route").\("routeLongName").as[String])
    .distinct
    .reduceLeft(_+", "+_)
  }
}
