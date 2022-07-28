@file:Suppress("VulnerableLibrariesLocal")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.qameta.allure-adapter") version "2.10.0"
    kotlin("plugin.jpa") version "1.6.21"
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

allure {
    adapter.autoconfigure
    adapter.aspectjWeaver
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
    implementation("org.postgresql:postgresql:42.4.0")
    implementation("io.qameta.allure:allure-kotlin-model:2.4.0")
    implementation("io.qameta.allure:allure-kotlin-commons:2.4.0")
    implementation("io.qameta.allure:allure-commandline:2.18.1")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("io.qameta.allure:allure-testng:2.18.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")
    testImplementation("ch.qos.logback:logback-classic:1.3.0-alpha16")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    val browser = System.getProperty("browser")
    systemProperties = mapOf("browser" to browser)
    val suite = System.getProperty("suite", "testng-all.xml")
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
        classpath("io.qameta.allure.gradle.adapter:allure-adapter-plugin:2.10.0")
    }
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "io.qameta.allure-adapter")
