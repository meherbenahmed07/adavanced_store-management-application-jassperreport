name := """advanced-store-management-application-jasperreport"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  filters
)
routesGenerator := InjectedRoutesGenerator
unmanagedJars in Compile += file("lib/bcpkix-jdk15on-1.52.jar")
unmanagedJars in Compile += file("lib/bcprov-jdk15on-1.52.jar")
unmanagedJars in Compile += file("lib/regkassen-loyalcraft-1.0.0.jar")
unmanagedJars in Compile += file("lib/regkassen-verification-common-1.0.10.jar")
libraryDependencies ++= Seq(
  //jdbc,
  cache,
  ws,
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.netflix.rxjava" % "rxjava-scala" % "0.20.7",
  "mysql" % "mysql-connector-java" % "6.0.3",
  "net.sf.jasperreports" % "jasperreports" % "6.2.2"  withSources()

)
resolvers += "Jasper" at "https://jaspersoft.artifactoryonline.com/jaspersoft/repo/"
resolvers += "JasperSoft" at "https://jaspersoft.artifactoryonline.com/jaspersoft/jaspersoft-repo/"
resolvers += "Jasper3rd" at "https://jaspersoft.artifactoryonline.com/jaspersoft/jaspersoft-3rd-party/"
resolvers += "mondrian-repo-cache" at "https://jaspersoft.artifactoryonline.com/jaspersoft/mondrian-repo-cache/"
resolvers += "spring-mil" at "http://repo.spring.io/libs-milestone"
resolvers += "spring-rel" at "http://repo.spring.io/libs-release"
resolvers += "oss" at "https://oss.sonatype.org/content/groups/public/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += DefaultMavenRepository
libraryDependencies ++= Common.commonDependencies