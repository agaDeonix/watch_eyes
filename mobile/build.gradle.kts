plugins {
    id(BuildPlugins.application)
    id(BuildPlugins.android)
}

android {
    namespace = Configs.namespace
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.applicationId
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        minSdk = Configs.minSdk

        testInstrumentationRunner = Configs.testInstrumentationRunner
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
    implementation(projects.common)
    implementation("androidx.core:core-ktx:+")

    constraints {
        implementation(Deps.StdLib.jdk7) {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation(Deps.StdLib.jdk8) {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    coreLibraryDesugaring(Deps.androidDesugarjdklibs)

    implementation(Deps.KotlinX.Coroutines.core)
    implementation(Deps.KotlinX.Coroutines.android)
    implementation(Deps.KotlinX.Coroutines.playServices)

    implementation(Deps.AndroidX.activityCompose)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.fragmentKtx)
    implementation(Deps.AndroidX.lifecycleViewmodelCompose)
    implementation(Deps.AndroidX.lifecycleRuntimeKtx)
    implementation(Deps.AndroidX.navigation)

    implementation(Deps.Compose.material)
    implementation(Deps.Compose.uiTooling)

    implementation(Deps.Google.playservicesWearable)
    implementation(Deps.Google.accompanistPager)

    implementation(Deps.DI.koin)
    implementation(Deps.DI.koinCompose)

    testImplementation(Deps.DI.koinTest)

    wearApp(projects.wear)
}
