/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package models

import java.sql.Timestamp

case class Search(
  source: String,
  fromName: String,
  fromLatLng: Option[LatLng],
  toName: String,
  toLatLng: Option[LatLng]
)


