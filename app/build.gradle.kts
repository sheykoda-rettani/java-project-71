plugins {
    id("java")
    id("com.github.ben-manes.versions") version "0.53.0"
    id("application")
    id("checkstyle")
    id("org.sonarqube") version "7.2.2.6593"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
}

application {
    mainClass = "hexlet.code.App"
}

sonar {
    properties {
        property("sonar.projectKey", "sheykoda-rettani_java-project-71")
        property("sonar.organization", "sheykoda-rettani")
    }
}

tasks.test {
    useJUnitPlatform()
}