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
