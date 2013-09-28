package models

import java.sql.Timestamp

case class Search(
  fromName: String,
  fromLatLng: Option[LatLng],
  toName: String,
  toLatLng: Option[LatLng]
)


