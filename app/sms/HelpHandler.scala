package sms

object HelpHandler extends StaticHandler {
  def keyword = "HELP"

  val reply = "To get directions, text FROM <from> TO <to>, example: "+
              "FROM ateneo TO UP.\n"+
              "To get jeeps passing near you, text NEAR <location>"
}
