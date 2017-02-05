MySQL API for Scala.js
=======================
This is a Scala.js type-safe binding for [mysql](https://www.npmjs.com/package/mysql) 

DataStax Node.js Driver for Apache Cassandra.

#### Build Dependencies

* [ScalaJs.io v0.3.x](https://github.com/ldaniels528/scalajs.io)
* [SBT v0.13.13](http://www.scala-sbt.org/download.html)

#### Build/publish the SDK locally

```bash
$ sbt clean publish-local
```

#### Running the tests

Before running the tests the first time, you must ensure the npm packages are installed:

```bash
$ npm install
```

Then you can run the tests:

```bash
$ sbt test
```

#### Examples

```scala
// establish a connection
val conn = MySQL.createConnection(
  new ConnectionOptions(
    host = "localhost",
    database = "tracking_data",
    user = "root",
    password = ""
  ))
      
// close connection after 60 seconds
setTimeout(() => {
  console.log("Closing connection...")
  conn.destroy()
}, 60.seconds)

// retrieve some records
val results = conn.query("SELECT * FROM activity LIMIT 1")
results.onFields(fields => console.log("fields => %j", fields.head))
results.onResult(console.log("row => %j", _))

// display the results
for {
  (rows, fields) <- conn.queryFuture[Activity]("SELECT * FROM activity LIMIT 1")
} {
  console.log("fields => %j", fields.head)
  console.log("rows => %j", rows.head)
}
```

#### Artifacts and Resolvers

To add the `mysql` binding to your project, add the following to your build.sbt:  

```sbt
libraryDependencies += "io.scalajs.npm" %%% "mysql" % "0.3.0.3"
```

Optionally, you may add the Sonatype Repository resolver:

```sbt   
resolvers += Resolver.sonatypeRepo("releases") 
```