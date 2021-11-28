pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.0"
    }
}

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            //Plugins
            alias("jmh").toPluginId("me.champeau.jmh").version("0.6.5")
            alias("buildinfo").toPluginId("org.kordamp.gradle.build-info").version("0.46.0")


            //Libs
            alias("logback").to("ch.qos.logback:logback-classic:1.2.6")
            alias("apache.lang").to("org.apache.commons:commons-lang3:3.12.0")
            alias("guava").to("com.google.guava:guava:31.0.1-jre")


            //Testing
            val junitVersion = "5.8.1"
            alias("junit").to("org.junit.jupiter:junit-jupiter:$junitVersion")
            alias("assertk").to("com.willowtreeapps.assertk:assertk-jvm:0.25")
            alias("mockk").to("io.mockk:mockk:1.12.1")
        }
    }
}

rootProject.name = "tablevis"
