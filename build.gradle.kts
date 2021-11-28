import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
}

group = "net.igsoft"
version = "0.5-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(libs.guava)
    implementation(libs.apache.lang)

    testImplementation(kotlin("test"))

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
}
