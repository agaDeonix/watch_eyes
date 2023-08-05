buildscript {
    val kotlin_version by extra("1.9.0")
    dependencies {
        classpath(Deps.classpathKotlinGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    id(BuildPlugins.library) version Versions.libraryPlugin apply false
    id(BuildPlugins.android) version Versions.androidPlugin apply false
    id(BuildPlugins.jvm) version Versions.jvmPlugin apply false
}
