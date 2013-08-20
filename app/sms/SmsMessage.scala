package sms

case class SmsMessage(source: String, body: String, target: String) {
  def reply(replyBody: String) = SmsMessage(target, replyBody, source)
}
