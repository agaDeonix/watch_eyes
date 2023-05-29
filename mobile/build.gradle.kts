plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.pinkunicorp.watch_eyes"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pinkunicorp.watch_eyes"
        versionCode = 1
        versionName = "1.0"
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        enable += "warningsAsErrors"
        disable += "ObsoleteLintCustomCheck"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xcontext-receivers",
        )
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
}

dependencies {
    implementation(project(":common"))

    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    coreLibraryDesugaring(Deps.androidDesugarjdklibs)

    implementation(Deps.kotlinxCoroutinesCore)
    implementation(Deps.kotlinxCoroutinesAndroid)
    implementation(Deps.kotlinxCoroutinesPlayServices)
    implementation(Deps.androidxActivityCompose)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeUiTooling)
    implementation(Deps.androidxCoreKtx)
    implementation(Deps.androidxFragmentKtx)
    implementation(Deps.androidxLifecycleViewmodelCompose)
    implementation(Deps.androidxLifecycleRuntimeKtx)
    implementation(Deps.playservicesWearable)

    val navVersion = "2.5.3"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")

    wearApp(projects.wear)
}
