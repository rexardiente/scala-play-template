name := """scala-play-template"""
organization := "rexardiente09@gmail.com"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.6"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
	guice,
	"com.typesafe.slick" %% "slick" % "3.3.3",
	"com.typesafe.play" %% "play-slick" % "5.0.0",
	"com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
	"com.github.tminglei" %% "slick-pg" % "0.19.7",
	"com.github.tminglei" %% "slick-pg_core" % "0.19.7",
	"com.github.tminglei" %% "slick-pg_play-json" % "0.19.7",
	"org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
// Adds additional packages into conf/routes
play.sbt.routes.RoutesKeys.routesImport ++= Seq(
	"models.utils.Binders._",
	"java.util.UUID"
)