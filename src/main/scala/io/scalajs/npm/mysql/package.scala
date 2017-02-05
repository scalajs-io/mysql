package io.scalajs.npm

import io.scalajs.nodejs.stream.Readable

import scala.scalajs.js

/**
  * MySQL package object
  * @author lawrence.daniels@gmail.com
  */
package object mysql {

  type RowDataPacket = js.Any

  /**
    * Stream Extensions
    * @author lawrence.daniels@gmail.com
    */
  implicit class StreamExtensions(val readable: Readable) extends AnyVal {

    /**
      * Emitted once to detail the field packets for the rows to follow
      */
    @inline
    def onFields(callback: js.Array[FieldPacket] => Any): readable.type = readable.on("fields", callback)

    /**
      * Emitted for each row of the result set
      */
    @inline
    def onResult[T <: RowDataPacket](callback: RowDataPacket => Any): readable.type = readable.on("result", callback)

  }

}
