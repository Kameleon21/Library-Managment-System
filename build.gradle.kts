import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm") version "1.9.0"
    // Plugin for Dokka - KDoc generation tool
    id("org.jetbrains.dokka") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    // For streaming to XML and YAML
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.yaml:snakeyaml:1.33")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}