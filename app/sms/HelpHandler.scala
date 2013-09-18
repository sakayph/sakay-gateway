package sms

object HelpHandler extends StaticHandler {
  def keyword = "HELP"

  val reply = "To get directions, text ROUTE <from> TO <to>, example: "+
              "ROUTE ateneo TO UP.\n"+
              "To get jeeps passing near you, text NEAR <location>"
}
