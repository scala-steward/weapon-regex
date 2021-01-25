// Skip publish root
skip in publish := true

inThisBuild(
  List(
    organization := "io.stryker-mutator",
    homepage := Some(url("https://github.com/Nhaajt/Weapon-regeX")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        id = "nhat",
        name = "Nhat",
        email = "",
        url = url("http://github.com/Nhaajt")
      ),
      Developer(
        id = "jan",
        name = "Jan",
        email = "",
        url = url("http://github.com/JSmits-utwente")
      ),
      Developer(
        id = "wijtse",
        name = "Wijtse",
        email = "",
        url = url("http://github.com/wijtserekker")
      )
    )
  )
)

lazy val WeaponRegeX = projectMatrix
  .in(file("core"))
  .settings(
    name := "weapon-regex",
    libraryDependencies += "com.lihaoyi" %%% "fastparse" % "2.3.0",
    libraryDependencies += "org.scalameta" %%% "munit" % "0.7.16" % Test,
    testFrameworks += new TestFramework("munit.Framework")
  )
  .jvmPlatform(
    scalaVersions = List("2.13.3", "2.12.12"),
    settings = Seq(
      // Add JVM-specific settings here
      libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
      jacocoReportSettings := JacocoReportSettings().withThresholds(JacocoThresholds(line = 80))
    )
  )
  .jsPlatform(
    scalaVersions = List("2.13.3", "2.12.12"),
    settings = Seq(
      // Add JS-specific settings here
      scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
    )
  )
