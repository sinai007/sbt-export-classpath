

lazy val root = Project("test-project", file(".")) dependsOn(sbtExportClasspath)
lazy val sbtExportClasspath = file("..").getAbsoluteFile.toURI
