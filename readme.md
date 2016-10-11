Sbt Export Classpath
--------------------

This SBT plugin allows you to export the classpath for each project.  By default, it exports the classpath into `target\.<config>-classpath`.  The classpath file is a simple list of all directories and jar files (absolute paths) that are configured to be in the classpath for the project.

Installation
------------

Install the plugin into your local ivy repository:
```
$> git clone <repo>
$> cd <repo>
$> sbt publishLocal
```

Configure the plugin to be automatically loaded for all sbt projects:
```
$> vi ~/.sbt/0.13/plugins/plugins.sbt
addSbtPlugin("com.github.sinail007" % "sbt-export-classpath" % "1.0.0")
$>
```

Usage
-----

Run the 'exportClasspath' task in the desired project:
```
sbt>  exportClasspath
sbt> test:exportClasspath
```

This will generate a .main-classpath and a .test-classpath for the project (and each subproject)

This classpath file can be used as a search index for IDEs and text editors such as Sublime

The classpath should be re-exported when dependencies change





