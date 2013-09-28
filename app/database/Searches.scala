package database

import java.sql.Timestamp

import anorm.SQL
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import models.Search

object Searches {
  def save(search: Search) = DB.withConnection { implicit c =>
    SQL("INSERT INTO searches VALUES (DEFAULT, {source}, DEFAULT, {fromName}, {fromLatitude}, {fromLongitude}, {toName}, {toLatitude}, {toLongitude})")
    .on(
      'source -> search.source,
      'fromName -> search.fromName,
      'fromLatitude -> search.fromLatLng.map(_.lat),
      'fromLongitude -> search.fromLatLng.map(_.lng),
      'toName -> search.toName,
      'toLatitude -> search.toLatLng.map(_.lat),
      'toLongitude -> search.toLatLng.map(_.lng)
    )
    .executeInsert()
  }
}
