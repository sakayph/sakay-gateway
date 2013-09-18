package sms

trait StaticHandler extends Handler {
  def reply: String
  def process(message: SmsMessage) = message.reply(reply)
}
