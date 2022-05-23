plugins {
    kotlin("jvm") version "1.6.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.21")
    testImplementation("org.slf4j:slf4j-simple:2.0.0-alpha7")
    testImplementation("org.testng:testng:7.5")
    implementation("org.seleniumhq.selenium:selenium-java:4.1.4")
    implementation("io.github.bonigarcia:webdrivermanager:5.1.1")
    implementation("org.aeonbits.owner:owner:1.0.12")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    compileOnly("org.projectlombok:lombok:1.18.24")
}

tasks.test {
    useTestNG {
        suites("src/test/resources/testng-all.xml")
        suiteXmlFiles = listOf(file("src/test/resources/testng-all.xml"))
//        listeners < < 'org.testng.reporters.XMLReporter:generateTestResultAttributes=true,generateGroupsAttribute=true'
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
