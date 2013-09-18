package sms

object AboutHandler extends StaticHandler {
  def keyword = "ABOUT"

  val reply = "Sakay is a project of By Implication. The route data "+
              "is provided by DOTC. Geolocation is provided by Google "+
              "Maps."
}
