plugins {
    id(BuildPlugins.application)
    id(BuildPlugins.android)
}

android {
//    signingConfigs {
//        getByName("debug") {
//            keyAlias = "debug"
//            keyPassword = "my debug key password"
//            storeFile = file("/home/miles/keystore.jks")
//            storePassword = "my keystore password"
//        }
//        create("release") {
//            keyAlias = "release"
//            keyPassword = "my release key password"
//            storeFile = file("/home/miles/keystore.jks")
//            storePassword = "my keystore password"
//        }
//    }
    namespace = Configs.namespace
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.applicationId
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        minSdk = Configs.minSdk
    }

    lint {
        enable += "warningsAsErrors"
        disable += "ObsoleteLintCustomCheck"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"

        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
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

    constraints {
        implementation(Deps.StdLib.jdk7) {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation(Deps.StdLib.jdk8) {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    implementation(Deps.KotlinX.Coroutines.core)
    implementation(Deps.KotlinX.Coroutines.android)
    implementation(Deps.KotlinX.Coroutines.playServices)

    implementation(Deps.AndroidX.activityCompose)
    implementation(Deps.AndroidX.lifecycleViewmodelCompose)
    implementation(Deps.AndroidX.lifecycleViewmodelKtx)

    implementation(Deps.Compose.uiTooling)
    implementation(Deps.Compose.foundation)

    implementation(Deps.Wear.Compose.material)
    implementation(Deps.Wear.Compose.foundation)

    implementation(Deps.Google.playservicesWearable)
}
