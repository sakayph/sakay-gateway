package database

import anorm.SQL
import play.api.Play.current
import play.api.db.DB

object Pending {
  def save(target: String, message: String) = DB.withConnection { implicit c =>
    SQL("INSERT INTO pending VALUES (DEFAULT, {target}, {message})")
    .on(
      'target -> target,
      'message -> message
    )
    .executeInsert()
  }
}
