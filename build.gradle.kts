val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val axon_version: String by project
val jackson_version: String by project
val axon_kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("io.ktor.plugin") version "2.1.1"
}

group = "dev.tagliaferro.cqrs.player"
version = "1.0.0"
application {
    mainClass.set("dev.tagliaferro.cqrs.player.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

ktor {
    fatJar {
        archiveFileName.set("player.jar")
    }
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-metrics-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Axon
    implementation("org.axonframework:axon-server-connector:$axon_version")
    implementation("org.axonframework:axon-configuration:$axon_version")
    implementation("org.axonframework.extensions.kotlin:axon-kotlin:$axon_kotlin_version")

    // Jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
}
