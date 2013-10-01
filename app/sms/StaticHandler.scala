/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

trait StaticHandler extends Handler {
  def reply: String
  def process(message: SmsMessage) = message.reply(reply)
}
