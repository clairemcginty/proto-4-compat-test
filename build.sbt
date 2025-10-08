import sbt.Keys._
import sbt._
import sbtprotobuf.ProtobufPlugin

lazy val Protobuf2Version = "2.6.1"
lazy val Protobuf3Version = "3.25.5"
lazy val Protobuf4Version = "4.33.0-RC1"
lazy val BeamVersion = "2.68.0"
lazy val TensorflowVersion = "0.4.2"
lazy val ScalatestVersion = "3.2.19"

val commonSettings = Seq(
  version := "0.1",
  organization := "com.spotify.test",
  scalaVersion := "2.13.16"
)


lazy val root = project
  .in(file("."))
  .aggregate(
    `schemas-proto-2`,
    `schemas-proto-3`,
    `schemas-proto-4`,
    `test-serialization-protobuf-java-3`,
    `test-serialization-protobuf-java-4`
  )

lazy val `schemas-proto-2` = project
  .in(file("schemas-proto-2"))
  .settings(commonSettings)
  .settings(
    ProtobufConfig / version := Protobuf2Version,
    libraryDependencies += "com.google.protobuf" % "protobuf-java" % Protobuf2Version
  )
  // No aarch-compat protoc for 2.x -- worked around by manually generating via protoc and checking into unmanaged source
  //.enablePlugins(ProtobufPlugin)

lazy val `schemas-proto-3` = project
  .in(file("schemas-proto-3"))
  .settings(commonSettings)
  .settings(
    ProtobufConfig / version := Protobuf3Version,
    libraryDependencies += "com.google.protobuf" % "protobuf-java" % Protobuf3Version
  )
  .enablePlugins(ProtobufPlugin)

lazy val `schemas-proto-4` = project
  .in(file("schemas-proto-4"))
  .settings(commonSettings)
  .settings(
    ProtobufConfig / version := Protobuf4Version,
    libraryDependencies += "com.google.protobuf" % "protobuf-java" % Protobuf4Version
  )
  .enablePlugins(ProtobufPlugin)

lazy val `test-serialization-protobuf-java-3` = project
  .in(file("test-serialization-protobuf-java-3"))
  .settings(commonSettings)
  .settings(
    dependencyOverrides += "com.google.protobuf" % "protobuf-java" % Protobuf3Version,
    libraryDependencies ++= Seq(
      "org.apache.beam" % "beam-sdks-java-core" % BeamVersion,
      "org.apache.beam" % "beam-sdks-java-extensions-protobuf" % BeamVersion,
      "org.tensorflow" % "tensorflow-core-api" % TensorflowVersion,
      "org.scalactic" %% "scalactic" % ScalatestVersion % Test,
      "org.scalatest" %% "scalatest" % ScalatestVersion % Test
    )
  )
  .dependsOn(`schemas-proto-2`, `schemas-proto-3`, `schemas-proto-4`)

lazy val `test-serialization-protobuf-java-4` = project
  .in(file("test-serialization-protobuf-java-4"))
  .settings(commonSettings)
  .settings(
    dependencyOverrides += "com.google.protobuf" % "protobuf-java" % Protobuf4Version,
    libraryDependencies ++= Seq(
      "org.apache.beam" % "beam-sdks-java-core" % BeamVersion,
      "org.apache.beam" % "beam-sdks-java-extensions-protobuf" % BeamVersion,
      "org.tensorflow" % "tensorflow-core-api" % TensorflowVersion,
      "org.scalactic" %% "scalactic" % ScalatestVersion % Test,
      "org.scalatest" %% "scalatest" % ScalatestVersion % Test
    )
  )
  .dependsOn(`schemas-proto-2`, `schemas-proto-3`, `schemas-proto-4`)