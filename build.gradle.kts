buildscript {
    dependencies {
        classpath(Deps.classpathKotlinGradlePlugin)
    }
}

plugins {
    id(BuildPlugins.library) version Versions.libraryPlugin apply false
    id(BuildPlugins.android) version Versions.androidPlugin apply false
    id(BuildPlugins.jvm) version Versions.jvmPlugin apply false
}
