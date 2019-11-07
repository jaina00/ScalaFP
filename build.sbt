name := "AkksTest"

version := "0.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)
val circeV = "0.10.1"
val akkaHttpV   = "10.0.8"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.16",
    "com.chuusai" %% "shapeless" % "2.3.3",
    "io.monix" %% "monix" % "3.0.0-RC2",
    "org.typelevel" %% "cats-free" % "1.6.0",
    "org.scalacheck" %% "scalacheck" % "1.14.0",
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "io.circe" %% "circe-jawn" % circeV,
    "org.typelevel" %% "cats-core" % "2.0.0-M1",
    "com.typesafe.akka" %% "akka-http" % akkaHttpV
  )
}

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)
addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.9" cross CrossVersion.binary)