import play.sbt.PlayImport.specs2
import sbt.Keys._
import sbt._


object Common {
  val commonDependencies = Seq(
    "com.querydsl" % "querydsl-jpa" % "4.1.3",
    "com.querydsl" % "querydsl-apt" % "4.1.3",
    "org.hibernate" % "hibernate-entitymanager" % "4.3.11.Final",
    "org.hibernate" % "hibernate-core" % "4.3.11.Final",
    "org.hibernate" % "hibernate-ehcache" % "4.3.11.Final",
    "org.hibernate" % "hibernate-validator" % "4.3.2.Final",
    "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
    "org.jadira.usertype" % "usertype.jodatime" % "2.0.1",
    "org.springframework.data" % "spring-data-commons" % "1.12.4.RELEASE",
    "com.cloudinary" % "cloudinary-http42" % "1.2.1",
    "com.sksamuel.scrimage" %% "scrimage-core" % "2.0.1", //Scala image processing library
    "com.roundeights" %% "hasher" % "1.2.0",
    "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5",
    "joda-time" % "joda-time" % "2.9.1",
    // send mail
    "com.typesafe.play" %% "play-mailer" % "5.0.0",
    //
    "com.stripe" % "stripe-java" % "2.7.0",
    "org.apache.poi" % "poi" % "3.8",
    //
    "org.springframework" % "spring-context" % "4.2.8.RELEASE",
    "org.springframework.data" % "spring-data-jpa" % "1.10.4.RELEASE",
    "org.springframework.data" % "spring-data-mongodb" % "1.9.7.RELEASE",
    //
    "mysql" % "mysql-connector-java" % "5.1.39",
    "cglib" % "cglib" % "3.2.3",
    "com.mchange" % "c3p0" % "0.9.5.2",
    //
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.7",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7",
    //slick
    "com.typesafe.play" %% "play-slick" % "2.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
    "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0",

    //spec2
    specs2 % Test
  )
  def settings() = Seq(
    organization := "loyalcraft.org",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.8",
    doc in Compile <<= target.map(_ / "none"),
    scalacOptions in ThisBuild ++= Seq(
      "-target:jvm-1.8",
      "-encoding", "UTF-8",
      "-deprecation", // warning and location for usages of deprecated APIs
      "-feature", // warning and location for usages of features that should be imported explicitly
      "-unchecked", // additional warnings where generated code depends on assumptions
      "-language:reflectiveCalls",
      "-Xlint", // recommended additional warnings
      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
      "-Ywarn-inaccessible",
      "-Ywarn-dead-code"
    )
  )
}
