import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    `maven-publish`
    signing
    id("me.champeau.jmh") version "0.6.6"
}

group = "net.igsoft"
version = "0.6.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    withJavadocJar()
    withSourcesJar()
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "tablevis"
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("TableVis table visualisation library")
                description.set("TableVis table visualisation library")
                url.set("https://github.com/aartiPl/tablevis/tree/master")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("aartiPl")
                        name.set("Marcin Kuszczak")
                        email.set("aarti@interia.pl")
                    }
                }
                scm {
                    connection.set("scm:git:git://https://github.com/aartiPl/tablevis.git")
                    developerConnection.set("scm:git:ssh:https://github.com/aartiPl/tablevis.git")
                    url.set("https://github.com/aartiPl/tablevis/tree/master")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            credentials {
                username = project.findProperty("sonatype.user") as String? ?: System.getenv("SONATYPE_USER")
                password = project.findProperty("sonatype.password") as String? ?: System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

jmh {
    warmupIterations.set(1)
    iterations.set(5)
    fork.set(2)
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")

    jmh("io.github.copper-leaf:krow-core:1.0.0")
    jmh("org.openjdk.jmh:jmh-core:1.34")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.34")
}
