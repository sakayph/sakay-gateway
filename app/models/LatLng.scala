package models

case class LatLng(lat: Double, lng: Double) {
  lazy val asString = lat+","+lng
}
