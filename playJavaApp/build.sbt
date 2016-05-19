name := """playJavaApp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mybatis" % "mybatis" % "3.3.0",
  "org.mybatis" % "mybatis-guice" % "3.6",
  "com.google.inject.extensions" % "guice-multibindings" % "4.0",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "redis.clients" % "jedis" % "2.7.3"
)

resolvers += "Nexus" at "http://nexus.nhry-dev.com/nexus/content/groups/public/"