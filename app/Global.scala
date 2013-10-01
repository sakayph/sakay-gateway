/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {
  override def onHandlerNotFound(request: RequestHeader) = {
    NotFound("404")
  }
}
