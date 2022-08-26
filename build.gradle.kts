@file:Suppress("VulnerableLibrariesLocal")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20-Beta"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.7.20-Beta"
    id("io.qameta.allure") version "2.10.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.4.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.2.3")
    implementation("org.aeonbits.owner:owner:1.0.12")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.testng:testng:7.6.1")
    implementation("org.postgresql:postgresql:42.4.2")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-mustache:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.3")
    implementation("org.springframework:spring-context:5.3.22")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20-Beta")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.20-Beta")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.20-Beta")
    runtimeOnly("com.h2database:h2:2.1.214")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("ru.yandex.qatools.ashot:ashot:1.5.4")
    implementation("io.qameta.allure:allure-testng:2.19.0")
    implementation("com.aventstack:extentreports:5.0.9")
}

tasks.test {
    val browser = System.getProperty("browser")
    systemProperties = mapOf("browser" to browser)
    val suite = System.getProperty("suite", "all-ui-tests.xml")
    useTestNG {
        suites("src/test/resources/TestXMLs/$suite")
    }
    testLogging { showStandardStreams = true }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
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
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.7.20-Beta")
    }
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

val allureVersion = "2.17.2"

allure {
}

configurations {
    create("testCompile")
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
