package com.github.sinai007

import java.io._

import sbt.Keys._
import sbt._

object SbtExportClasspathPlugin extends AutoPlugin {

  object autoImport {
    val classpathFile = settingKey[File]("The name of the file where the classpath is saved")
    val exportClasspath = taskKey[Unit]("Exports the classpath")

    // val configurationname = Def.taskDyn {
    //     configuration
    // }
    def exportClasspathSettings(configType: String): Seq[Def.Setting[_]] = Seq(
      exportClasspath <<= (configuration, Keys.target, dependencyClasspath, Keys.classDirectory, sbt.Keys.streams) map { (configuration, target, cp, cd, streams) =>
        exportClasspathImpl(new File(s"${target}/.${configuration.name}-classpath"), cp, cd, streams)
      }
        // val cfg = configuration.?.value
        // println(s"Configuration ${cfg}")

        //ClassIndexer((indexFile in indexClasses).value, dependencyClasspath.value, Keys.classDirectory.value, sbt.Keys.streams.value).index()
      // },
      // classpathFile <<= (configuration, Keys.target) map { cfg, target =>
        // target / s".${cfg}-classpath")
      // }
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

