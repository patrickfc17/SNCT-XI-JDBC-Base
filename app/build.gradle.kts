/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
    implementation("org.xerial:sqlite-jdbc:3.7.2")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = System.getProperty("exec.mainClass") ?: "org.example.App"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.register<JavaExec>("migrate") {
    group = "Database Migrations"
    description = "Executa as migrations pendentes"
    mainClass.set("org.example.database.console.UserMigration")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("rollback") {
    group = "Database Migrations"
    description = "Executa os rollbacks das migrations já efetuadas"
    mainClass.set("org.example.database.console.UserMigration")
    classpath = sourceSets.main.get().runtimeClasspath
    args = listOf("rollback")
}

tasks.register<JavaExec>("seed") {
    group = "Database Seeders"
    description = "Executa os seeders"
    mainClass.set("org.example.database.console.UserSeeder")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("cleanup") {
    group = "Database Seeders"
    description = "Executa os seeders"
    mainClass.set("org.example.database.console.UserSeeder")
    classpath = sourceSets.main.get().runtimeClasspath
    args = listOf("clean")
}
