/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
import anorm._
import java.sql.Timestamp

package object database {

  implicit def rowToTimestamp: Column[Timestamp] = {
    Column[Timestamp](transformer = { (value, meta) =>
      val MetaDataItem(qualified,nullable,clazz) = meta
      value match {
        case time:Timestamp => Right(time)
        case _ => Left(TypeDoesNotMatch("Cannot convert " + value + " to Timestamp for column " + qualified))
      }
    })
  }

}
