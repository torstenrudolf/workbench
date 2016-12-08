import sbt.Keys._

val defaultSettings = Seq(
  scalacOptions ++= Seq("-feature", "-deprecation"),
  unmanagedSourceDirectories in Compile += baseDirectory.value /  "shared" / "main" / "scala",
  unmanagedSourceDirectories in Test += baseDirectory.value / "shared" / "test" / "scala"
)

lazy val root = project.in(file(".")).settings(defaultSettings:_*).settings(
  name := "workbench",
  version := "0.3.1b-SNAPSHOT",
//  organization := "com.lihaoyi",
  organization := "com.github.torstenrudolf",
  scalaVersion := "2.10.6",
  sbtPlugin := true,
  publishArtifact in Test := false,
//  publishTo := Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),
  publishTo := Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"),
  pomExtra := (
    <url>https://github.com/lihaoyi/workbench</url>
      <licenses>
        <license>
          <name>MIT license</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/lihaoyi/workbench.git</url>
        <connection>scm:git://github.com/lihaoyi/workbench.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lihaoyi</id>
          <name>Li Haoyi</name>
          <url>https://github.com/lihaoyi</url>
        </developer>
      </developers>
  ),
  (resources in Compile) += {
    (fullOptJS in (client, Compile)).value
    (artifactPath in (client, Compile, fullOptJS)).value
  },
  addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.13"),
  libraryDependencies ++= Seq(
    Dependencies.sprayCan,
    Dependencies.sprayRouting,
    Dependencies.akka,
    Dependencies.autowire.value,
    Dependencies.upickle.value
  )
)

lazy val client = project.in(file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(defaultSettings: _*)
  .settings(
    unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "shared" / "main" / "scala",
    libraryDependencies ++= Seq(
      Dependencies.autowire.value,
      Dependencies.dom.value,
      Dependencies.upickle.value
    ),
    emitSourceMaps := false
  )
