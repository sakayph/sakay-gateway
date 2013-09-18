package database

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import sms.SmsMessage

object Messages {
  def saveIncoming(message: SmsMessage) = DB.withConnection { implicit c =>
    SQL("INSERT INTO incoming VALUES (DEFAULT, DEFAULT, {source}, {message}, {target})")
    .on(
      'source -> message.source,
      'message -> message.body,
      'target -> message.target
    )
    .executeInsert()
  }

  def saveOutgoing(message: SmsMessage, replyTo: Option[Long]) = DB.withConnection { implicit c =>
    SQL("INSERT INTO outgoing VALUES (DEFAULT, DEFAULT, {source}, {message}, {target}, {replyTo})")
    .on(
      'source -> message.source,
      'message -> message.body,
      'target -> message.target,
      'replyTo -> replyTo
    )
    .executeInsert()
  }
}
