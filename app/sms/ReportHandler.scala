/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

object ReportHandler extends StaticHandler {
  def keyword = "REPORT"

  val reply = "Thanks for your feedback! We appreciate it."
}
