package io.scalajs.npm.mysql

import io.scalajs.nodejs.Error
import io.scalajs.nodejs.events.IEventEmitter
import io.scalajs.nodejs.stream.Readable
import io.scalajs.util.PromiseHelper._

import scala.concurrent.Future
import scala.scalajs.js

/**
  * MySQL Connection
  * @author lawrence.daniels@gmail.com
  */
@js.native
trait Connection extends IEventEmitter {

  /////////////////////////////////////////////////////////////////////////////////
  //      Properties
  /////////////////////////////////////////////////////////////////////////////////

  var config: ConnectionOptions = js.native

  /////////////////////////////////////////////////////////////////////////////////
  //      Methods
  /////////////////////////////////////////////////////////////////////////////////

  /**
    * Starts a new transaction
    * @example connection.beginTransaction(function(err) { ... })
    */
  def beginTransaction(callback: js.Function): Unit = js.native

  /**
    * MySQL offers a changeUser command that allows you to alter the current user and other aspects of the
    * connection without shutting down the underlying socket:
    * @example {{{
    *   connection.changeUser({user : 'john'}, function(err) {
    *     if (err) throw err;
    *   });
    * }}}
    * @param options the given [[ChangeUserOptions]]
    */
  def changeUser(options: ChangeUserOptions, callback: js.Function): Unit = js.native

  /**
    * Commits a transaction
    * @param callback the error callback
    * @example commit(function(err) { ... })
    */
  def commit(callback: js.Function): Unit = js.native

  /**
    * Creates a new connection
    * @param callback the results callback
    */
  def connect(callback: js.Function): Unit = js.native

  /**
    * An alternative way to end the connection is to call the destroy() method. This will cause an immediate
    * termination of the underlying socket. Additionally destroy() guarantees that no more events or callbacks
    * will be triggered for the connection.
    */
  def destroy(): Unit = js.native

  /**
    * There are two ways to end a connection. Terminating a connection gracefully is done by calling the end() method
    * @param callback the error callback
    * @example connection.end(function(err) { ... })
    */
  def end(callback: js.Function): Unit = js.native

  /**
    * A ping packet can be sent over a connection using the connection.ping method. This method will send a ping packet
    * to the server and when the server responds, the callback will fire. If an error occurred, the callback will fire
    * with an error argument.
    * @example connection.ping(function (err) { ... })
    */
  def ping(callback: js.Function): Unit = js.native

  /**
    * Executes a query and streams the results
    * @param query the given query string
    * @return a readable for streaming the results
    */
  def query(query: String): Readable = js.native

  /**
    * Asynchronously executes a query
    * @param query    the given query string
    * @param callback the results callback
    * @example connection.query('INSERT INTO log SET data=?', log, function(err, result) { ... })
    */
  def query(query: String, callback: js.Function): Unit = js.native

  /**
    * Asynchronously executes a query
    * @param query    the given query string
    * @param params   the given query parameters
    * @param callback the results callback
    * @example connection.query('INSERT INTO log SET data=?', log, function(err, result) { ... })
    */
  def query[T <: js.Any](query: String, params: js.Array[T], callback: js.Function): Unit = js.native

  /**
    * Asynchronously executes a query
    * @param options  the given query options
    * @param callback the results callback
    * @example connection.query(options, function(err, results) { ... })
    */
  def query(options: QueryOptions, callback: js.Function): Unit = js.native

  /**
    * Asynchronously executes a query
    * @param options  the given query options
    * @param params   the given query parameters
    * @param callback the results callback
    * @example connection.query(options, log, function(err, result) { ... })
    */
  def query[T <: js.Any](options: QueryOptions, params: js.Array[T], callback: js.Function): Unit = js.native

  /**
    * When you are done with a connection, just call connection.release() and the connection will return to the pool,
    * ready to be used again by someone else.
    */
  def release(): Unit = js.native

  /**
    * Rolls back a transaction
    * @param callback the response callback (empty arguments)
    */
  def rollback(callback: js.Function): Unit = js.native

}

/**
  * Connection Companion
  * @author lawrence.daniels@gmail.com
  */
object Connection {

  /**
    * Connection Extensions
    * @author lawrence.daniels@gmail.com
    */
  implicit class ConnectionExtensions(val connection: Connection) extends AnyVal {

    @inline
    def beginTransactionFuture(): Future[Unit] = promiseWithError0[Error](connection.beginTransaction(_))

    @inline
    def changeUserFuture(options: ChangeUserOptions): Future[Unit] = promiseWithError0[Error](connection.changeUser(options, _))

    @inline
    def commitFuture(): Future[Unit] = promiseWithError0[Error](connection.commit(_))

    @inline
    def connectFuture(): Unit = promiseWithError0[Error](connection.connect(_))

    @inline
    def endFuture(): Future[Unit] = promiseWithError0[Error](connection.end(_))

    @inline
    def executeFuture(query: String): Future[OkPacket] = promiseWithError1[Error, OkPacket](connection.query(query, _))

    @inline
    def executeFuture(options: QueryOptions): Future[OkPacket] = promiseWithError1[Error, OkPacket](connection.query(options, _))

    @inline
    def executeFuture[T <: js.Any](query: String, params: js.Array[T]): Future[OkPacket] = {
      promiseWithError1[Error, OkPacket](connection.query(query, params, _))
    }

    @inline
    def pingFuture(): Future[Unit] = promiseWithError0[Error](connection.ping(_))

    @inline
    def queryFuture[T <: RowDataPacket](query: String): Future[(js.Array[T], js.Array[FieldPacket])] = {
      promiseWithError2[Error, js.Array[T], js.Array[FieldPacket]](connection.query(query, _))
    }

    @inline
    def queryFuture[S <: js.Any, T <: RowDataPacket](query: String, params: js.Array[S]): Future[(js.Array[T], js.Array[FieldPacket])] = {
      promiseWithError2[Error, js.Array[T], js.Array[FieldPacket]](connection.query(query, params, _))
    }

    @inline
    def queryFuture[T <: RowDataPacket](options: QueryOptions): Future[(js.Array[T], js.Array[FieldPacket])] = {
      promiseWithError2[Error, js.Array[T], js.Array[FieldPacket]](connection.query(options, _))
    }

    @inline
    def rollbackFuture(): Future[Unit] = promiseCallback0(connection.rollback)

  }

}