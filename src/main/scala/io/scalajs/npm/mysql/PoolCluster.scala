package io.scalajs.npm.mysql

import io.scalajs.nodejs.events.IEventEmitter

import scala.scalajs.js

/**
  * Pool Cluster
  * @author lawrence.daniels@gmail.com
  */
@js.native
trait PoolCluster extends IEventEmitter {

  def add(config: js.Any): Unit = js.native

  def add(name: String, config: js.Any): Unit = js.native

  def end(callback: js.Function): Unit = js.native

  def getConnection(name: String, `type`: String, callback: js.Function): Unit = js.native

  def getConnection(name: String, callback: js.Function): Unit = js.native

  def getConnection(callback: js.Function): Unit = js.native

  def of(name: String, `type`: String): Pool = js.native

  def of(name: String): Pool = js.native

  def remove(name: String): Unit = js.native

}

/**
  * Pool Cluster
  * @author lawrence.daniels@gmail.com
  */
object PoolCluster {

  /**
    * Pool Cluster Extensions
    * @author lawrence.daniels@gmail.com
    */
  implicit class PoolClusterExtensions(val cluster: PoolCluster) extends AnyVal {

    def onRemove(callback: js.Function): cluster.type = cluster.on("remove", callback)

  }

}