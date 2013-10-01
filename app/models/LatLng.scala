/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package models

case class LatLng(lat: Double, lng: Double) {
  lazy val asString = lat+","+lng
}
