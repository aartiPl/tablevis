import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
}

group = "net.igsoft"
version = "0.5-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    create("example") {
        java.srcDir("src/example/kotlin")
        compileClasspath += sourceSets.getByName("main").output + sourceSets.getByName("main").compileClasspath
        runtimeClasspath += sourceSets.getByName("main").output + sourceSets.getByName("main").runtimeClasspath
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
}
