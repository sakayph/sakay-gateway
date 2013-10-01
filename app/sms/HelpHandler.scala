/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package sms

object HelpHandler extends StaticHandler {
  def keyword = "HELP"

  val reply = "To get directions, text ROUTE <from> TO <to>, example: "+
              "ROUTE ateneo TO up diliman.\n"+
              "To get jeeps passing near you, text NEAR <location>"
}
