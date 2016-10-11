package com.github.sinai007

import java.io._

import sbt.Keys._
import sbt._

object SbtExportClasspathPlugin extends AutoPlugin {

  object autoImport {
    val classpathFile = settingKey[File]("The name of the file where the classpath is saved")
    val exportClasspath = taskKey[Unit]("Exports the classpath")

    def exportClasspathSettings(configType: String): Seq[Def.Setting[_]] = Seq(
      exportClasspath := {
        exportClasspathImpl(classpathFile.value, dependencyClasspath.value, Keys.classDirectory.value, sbt.Keys.streams.value)
        //ClassIndexer((indexFile in indexClasses).value, dependencyClasspath.value, Keys.classDirectory.value, sbt.Keys.streams.value).index()
      },
      classpathFile <<= Keys.target(_ / s".${configType}-classpath")
    )
  }

  import autoImport._

  override def trigger = allRequirements

  override val projectSettings =
    inConfig(Compile)(exportClasspathSettings("main")) ++
      inConfig(Test)(exportClasspathSettings("test"))


  def exportClasspathImpl(classpath: File, dependencies: sbt.Keys.Classpath, target: File, streams: sbt.Keys.TaskStreams) = {
      val pw = new PrintWriter(new FileWriter(classpath))
      //put the current project output first
      pw.println(target.getAbsolutePath())
      dependencies.files.foreach { f =>
        pw.println(f.getAbsolutePath())
      }
      streams.log.info(s"Generated classpath file ${classpath}")
      pw.close()

  }
}

