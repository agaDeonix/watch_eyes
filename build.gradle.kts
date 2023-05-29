buildscript {
    dependencies {
        classpath(Deps.kotlinGradlePlugin)
    }
}

plugins {
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
}
