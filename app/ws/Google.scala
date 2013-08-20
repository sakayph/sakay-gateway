package ws

import java.text.SimpleDateFormat
import java.util.Date
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.control.Exception._
import play.api.Play.current
import play.api.libs.ws.WS
import models.LatLng

object Google {

  lazy val API = "https://maps.googleapis.com"
  lazy val KEY = current.configuration.getString("google.maps_api.key").get

  val dateFormat = new SimpleDateFormat("yyyy-M-d")

  def geocode(name: String): Option[LatLng] = allCatch opt {
    val request = WS.url(API+"/maps/api/place/autocomplete/json").withQueryString(
      "key" -> KEY,
      "sensor" -> "false",
      "components" -> "country:ph",
      "input" -> name
    ).get()

    val result = Await.result(request, 10 seconds)
    val info = details(result.json.\("predictions")(0).\("reference").as[String])
    val loc = info.json \ "result" \ "geometry" \ "location"
    LatLng(
      (loc \ "lat").as[Double],
      (loc \ "lng").as[Double]
    )
  }

  private def details(reference: String) = {
    val request = WS.url(API+"/maps/api/place/details/json").withQueryString(
      "key" -> KEY,
      "sensor" -> "false",
      "reference" -> reference
    ).get()

    Await.result(request, 10 seconds)
  }
}
