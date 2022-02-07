@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    id("com.gorylenko.gradle-git-properties") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("com.diffplug.spotless") version "6.2.1"
}

group = "com.github.lawkai"

repositories {
    mavenCentral()
}

val kotlinx_version = "1.6.0"
val http4k_version = "4.19.1.0"
val slf4j_version = "1.7.35"
val logback_version = "1.2.10"
val arrow_version = "1.0.1"

dependencies {
    // kotlin
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$kotlinx_version"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // arrow
    implementation(platform("io.arrow-kt:arrow-stack:$arrow_version"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    // logging
    implementation("org.slf4j:slf4j-api:$slf4j_version")
    runtimeOnly("ch.qos.logback:logback-classic:$logback_version")

    // http4k
    implementation(platform("org.http4k:http4k-bom:$http4k_version"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-cloudnative")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-contract")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-resilience4j")

    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-testing-strikt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("com.github.lawkai.AppKt")
}

tasks.named<Jar>("shadowJar") {
    archiveBaseName.set("App")
    archiveClassifier.set("")
    archiveVersion.set("")
}

spotless {
    encoding("UTF-8")
    kotlin {
        ktfmt().dropboxStyle()
        ktlint()
        // configure in diktat-analysis.yml(https://github.com/analysis-dev/diktat)
        diktat()
    }
    kotlinGradle {
        ktlint()
    }
    json {
        target("src/**/*.json")
        simple()
    }
}
