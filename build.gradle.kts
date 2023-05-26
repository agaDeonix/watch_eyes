buildscript {
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.com.diffplug.spotless) apply (false)
    alias(libs.plugins.com.android.application) apply (false)
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
}

subprojects {

    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target ("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint(libs.versions.ktlint.get())
            licenseHeaderFile(rootProject.file("../spotless/copyright.kt"))
        }
    }
}
