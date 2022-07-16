@file:Suppress("VulnerableLibrariesLocal")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.2.1")
    implementation("io.github.bonigarcia:webdrivermanager:5.2.0")
    implementation("org.aeonbits.owner:owner:1.0.12")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    implementation("org.testng:testng:7.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")
    testImplementation("ch.qos.logback:logback-classic:1.3.0-alpha16")
}

tasks.test {
    val browser = System.getProperty("browser")
    systemProperties = mapOf("browser" to browser)
    val suite = System.getProperty("suite", "testng-all.xml")
    useTestNG {
        suites("src/test/resources/$suite")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

buildscript {
    configurations {
        classpath {
            exclude(group = "org.slf4j", module = "slf4j-log4j12")
            exclude(group = "lgo4j", module = "log4j")
        }
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.3.0")
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
