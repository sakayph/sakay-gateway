package sms

import play.api.libs.json._
import ws.{OpenTripPlanner,Google}
import models.LatLng

object RouteHandler extends Handler {
  def keyword = "ROUTE"
  def process(message: SmsMessage) = {
    val response = parseMessage(message.body) match {
      case None => Left("Incorrect format. Message should be ROUTE <start> TO <end>")
      case Some((from, to)) => Right((Google.geocode(from), Google.geocode(to)))
    }

    val reply = response
    .right.flatMap {
      case (Some(from), Some(to)) => Right(OpenTripPlanner.plan(from, to))
      case _ => Left("Could not find a location. Try again with a different spelling.")
    }
    .right.flatMap {
      case Some(response) => Right(formatItinerary((response.json \ "plan" \ "itineraries")(0)))
      case None => Left("Could not find a route.")
    }
    .merge

    message.reply(reply)
  }

  def parseMessage(body: String): Option[(String, String)] = {
    val cleaned = body.dropWhile(_ != ' ').toLowerCase.trim
    val pos = cleaned.indexOf(" to ")
    if(pos < 0) {
      None
    }
    else {
      val from = cleaned.substring(0, pos)
      val to = cleaned.substring(pos+4)
      Some((from, to))
    }
  }

  def formatItinerary(itinerary: JsValue) = {
    val legs = (itinerary \ "legs").asInstanceOf[JsArray].value
    legs.map(formatLeg).reduceLeft(_+"\n"+_)
  }

  def formatLeg(leg: JsValue) = {
    val mode = leg.\("mode").as[String]
    val route = leg.\("route").as[String]
    val to = shortenAddress(leg.\("to").\("name").as[String])

    if(mode == "WALK") {
      "walk to "+to
    }
    else {
      "ride "+route+" to "+to
    }
  }

  // hacky solution ATM
  def shortenAddress(address: String) = {
    address
    .replace("Epifanio de los Santos Avenue", "EDSA")
    .replace("Epifanio de los Santos Ave", "EDSA")
    .replace("Epifanio de los Santos Av", "EDSA")
    .replace(" Intersection", "")
    .replace("Avenue", "Ave")
    .replace("Quezon City", "QC")
    .replace("Makati City", "Makati")
    .replace("Mandaluyong City", "Mandaluyong")
    .replace(", Manila", "")
  }
}
