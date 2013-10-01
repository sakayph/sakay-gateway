/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package database

import java.sql.Timestamp

import anorm.SQL
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import sms.SmsMessage

object Messages {
  def saveIncoming(message: SmsMessage) = DB.withConnection { implicit c =>
    SQL("INSERT INTO incoming VALUES (DEFAULT, {timestamp}, {source}, {message}, {target})")
    .on(
      'timestamp -> message.timestamp,
      'source -> message.source,
      'message -> message.body,
      'target -> message.target
    )
    .executeInsert()
  }

  def saveOutgoing(message: SmsMessage, replyTo: Option[Long]) = DB.withConnection { implicit c =>
    SQL("INSERT INTO outgoing VALUES (DEFAULT, {timestamp}, {source}, {message}, {target}, {replyTo})")
    .on(
      'timestamp -> message.timestamp,
      'source -> message.source,
      'message -> message.body,
      'target -> message.target,
      'replyTo -> replyTo
    )
    .executeInsert()
  }

  def list(since: Long) = DB.withConnection { implicit c =>
    SQL("""
      SELECT * FROM incoming
        LEFT JOIN outgoing ON incoming.id = outgoing.reply_to
      WHERE incoming.timestamp < {since}
      ORDER BY incoming.timestamp DESC
      LIMIT 10
    """)
    .on('since -> new Timestamp(since))
    .list(
      incomingMessageParser ~
      outgoingMessageParser.? map(flatten)
    )
  }

  def incomingMessageParser =
    get[Timestamp]("incoming.timestamp") ~
    str("incoming.source") ~
    str("incoming.message") ~
    str("incoming.target") map(flatten) map((SmsMessage.apply _).tupled)

  def outgoingMessageParser =
    get[Timestamp]("outgoing.timestamp") ~
    str("outgoing.source") ~
    str("outgoing.message") ~
    str("outgoing.target") map(flatten) map((SmsMessage.apply _).tupled)

}
