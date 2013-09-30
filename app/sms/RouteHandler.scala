package sms

import play.api.libs.json._
import ws.{OpenTripPlanner,Google}
import models.{LatLng,Search,BusFare,RailFare}
import database.Searches

object RouteHandler extends Handler {
  def keyword = "ROUTE"
  def process(message: SmsMessage) = {
    val parsedMessage = parseMessage(message.body)
    val response = parsedMessage match {
      case None => Left("Incorrect format. Message should be ROUTE <start> TO <end>")
      case Some((from, to)) => Right((Google.geocode(from), Google.geocode(to)))
    }

    for {
      address <- parsedMessage
      latlng <- response.right.toOption
    } {
      val (fromName, toName) = address
      val (fromLatLng, toLatLng) = latlng
      Searches.save(Search("sms", fromName, fromLatLng, toName, toLatLng))
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
    val totalCost = legs.map(getFare).sum
    var output = legs.zipWithIndex
      .filter {
        case (leg, 0) => true
        case (leg, n) => leg.\("duration").as[Double] > 60000
      }
      .map(formatLeg).reduceLeft(_+"\n"+_)

    val incomplete = legs.count { leg =>
      leg.\("mode").as[String] == "RAIL" && leg.\("routeId").as[String] == "ROUTE_880872"
    } > 0

    if(totalCost > 0) {
      output += "\nFare: %.2f".format(totalCost)
    }
    if(totalCost > 0 && incomplete) {
      output += "*"
    }
    output
  }

  def formatLeg(tuple: (JsValue, Int)) = {
    val (leg, index) = tuple
    val mode = leg.\("mode").as[String]
    val route = leg.\("route").as[String]
    val to = shortenAddress(leg.\("to").\("name").as[String])

    val direction = if(mode == "WALK") {
      "go to "+to
    }
    else {
      "ride "+route+" to "+to
    }
    (index+1)+". "+direction
  }

  def getFare(leg: JsValue) = {
    def getStop(on: String) = leg.\(on).\("stopId").\("id").as[String]
    val mode = leg.\("mode").as[String]
    if(mode == "BUS" || mode == "RAIL") {
      val routeId = leg.\("routeId").as[String]

      mode match {
        case "BUS" =>
          val typ = if(routeId.contains("PUJ")) "puj" else "pub_aircon"
          BusFare.get(typ, leg.\("distance").as[Double]).regular
        case "RAIL" =>
          RailFare.get(routeId, getStop("from"), getStop("to")).regular
      }
    }
    else {
      0
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
