plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

apply(plugin = "com.github.johnrengelman.shadow")

repositories {
    mavenCentral()
}

javafx {
    version = "22"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.apache.poi:poi-ooxml:4.1.2")
    implementation("org.apache.poi:poi-ooxml-schemas:4.1.2")
    implementation("org.apache.xmlbeans:xmlbeans:3.1.0")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("app.ui.ApplicationKt")
}

open class CreateIconSet : DefaultTask() {
    @TaskAction
    fun build() {
        val iconDirPath = "src/main/resources/MyIcon.iconset"
        val iconDir = File(iconDirPath)
        iconDir.mkdirs()
        listOf(
            listOf(16, false),
            listOf(32, true),
            listOf(32, false),
            listOf(64, true),
            listOf(64, false),
            listOf(128, false),
            listOf(256, true),
            listOf(256, false),
            listOf(512, true),
            listOf(512, false),
            listOf(1028, false),
        ).forEach { items ->
            val size = items.first().toString()
            val halfSize = items.first() as Int / 2
            val double = items.last() as Boolean
            val name = if (double) "${halfSize}x$halfSize@2" else "${size}x$size"

            project.exec {
                executable("sips")
                args(
                    "-z",
                    size,
                    size,
                    "src/main/resources/icon.png",
                    "--out",
                    "$iconDirPath/icon_$name.png"
                )
            }
        }
        project.exec {
            executable("iconutil")
            args(
                "-c",
                "icns",
                "src/main/resources/MyIcon.iconset"
            )
        }
        iconDir.deleteRecursively()
    }
}

tasks.register<CreateIconSet>("createIcons") {
    group = "distribution"
}

open class PackageTask : DefaultTask() {
    @TaskAction
    fun build() {
        val icon = if (System.getProperty("os.name").lowercase().contains("win")) {
            "src/main/resources/icon.png"
        } else "src/main/resources/MyIcon.icns"

        project.exec {
            executable("jpackage")
            args(
                "--input",
                "build/libs",
                "--name",
                "Prayer Importer",
                "--dest",
                "build",
                "--main-jar",
                "prayer-importer-all.jar",
                "--main-class",
                "app.ui.ApplicationKt",
                "--type",
                "dmg",
                "--icon",
                icon
            )
        }
    }
}

tasks.register<PackageTask>("package") {
    group = "distribution"
    dependsOn(":shadowJar")
}
