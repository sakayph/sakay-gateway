/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

trait Handler {
  def keyword: String
  def process(message: SmsMessage): SmsMessage
}

object Handler {
  private var mapping = Map.empty[String, Handler]

  def register(handler: Handler) = {
    mapping += (handler.keyword -> handler)
  }

  def process(message: SmsMessage): SmsMessage = {
    val keyword = message.body.takeWhile(_ != ' ').toUpperCase
    mapping.get(keyword).getOrElse(HelpHandler).process(message)
  }

  register(RouteHandler)
  register(NearHandler)
  register(AboutHandler)
  register(HelpHandler)
  register(ReportHandler)
}
