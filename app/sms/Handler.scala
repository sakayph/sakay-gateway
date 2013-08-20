package sms

trait Handler {
  def keyword: String
  def process(message: SmsMessage): SmsMessage
}

object ErrorHandler extends Handler {
  def keyword = "ERROR"
  def process(message: SmsMessage) = message.reply("error")
}

object Handler {
  private var mapping = Map.empty[String, Handler]

  def register(handler: Handler) = {
    mapping += (handler.keyword -> handler)
  }

  def process(message: SmsMessage): SmsMessage = {
    val keyword = message.body.takeWhile(_ != ' ').toUpperCase
    mapping.get(keyword).getOrElse(ErrorHandler).process(message)
  }

  register(RouteHandler)
}
