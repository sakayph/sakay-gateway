/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

import java.sql.Timestamp

import play.api.libs.json._

case class SmsMessage(timestamp: Timestamp, source: String, body: String, target: String) {
  def reply(replyBody: String) = SmsMessage(new Timestamp(System.currentTimeMillis), target, replyBody, source)
}

object SmsMessage {
  implicit object ReadWrites extends Writes[SmsMessage] {
    def writes(o: SmsMessage) = Json.obj(
      "timestamp" -> o.timestamp,
      "source" -> o.source,
      "body" -> o.body,
      "target" -> o.target
    )
  }
}
