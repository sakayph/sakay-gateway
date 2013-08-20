package ws

import java.text.SimpleDateFormat
import java.util.Date
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.control.Exception._
import play.api.Play.current
import play.api.libs.ws.WS
import models.LatLng

object OpenTripPlanner {

  lazy val API = current.configuration.getString("opentripplanner.api").get

  val dateFormat = new SimpleDateFormat("yyyy-M-d")

  def plan(from: LatLng, to: LatLng) = allCatch opt {
    val request = WS.url(API+"/plan").withQueryString(
      "date" -> dateFormat.format(new Date()),
      "time" -> "11:59am",
      "fromPlace" -> from.asString,
      "toPlace" -> to.asString,
      "transferPenalty" -> "600"
    ).get()

    Await.result(request, 20 seconds)
  }
}
