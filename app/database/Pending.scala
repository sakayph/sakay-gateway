package database

import anorm.SQL
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json._

object Pending {
  def save(target: String, message: String) = DB.withConnection { implicit c =>
    SQL("INSERT INTO pending VALUES (DEFAULT, {target}, {message})")
    .on(
      'target -> target,
      'message -> message
    )
    .executeInsert()
  }

  def list() = DB.withConnection { implicit c =>
    val toSend = SQL("SELECT * FROM pending").list(
      long("id") ~
      str("target") ~
      str("message") map(flatten)
    )

    if(toSend.size > 0) {
      val maxId = toSend.map(_._1).max
      SQL("DELETE FROM pending WHERE id <= {id}")
      .on('id -> maxId)
      .executeUpdate()
    }

    toSend.map { case (_, target, message) =>
      Json.obj(
        "target" -> target,
        "message" -> message
      )
    }
  }
}
