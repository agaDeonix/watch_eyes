plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    namespace = "com.pinkunicorp.watch_eyes"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pinkunicorp.watch_eyes"
        versionCode = 1
        versionName = "1.0"

        minSdk = 26
        targetSdk = 30
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
    implementation(project(":common"))
    implementation(Deps.kotlinxCoroutinesCore)
    implementation(Deps.kotlinxCoroutinesAndroid)
    implementation(Deps.kotlinxCoroutinesPlayServices)
    implementation(Deps.androidxActivityCompose)
    implementation(Deps.composeUiTooling)
    implementation(Deps.composeFoundation)
    implementation(Deps.androidxLifecycleViewmodelCompose)
    implementation(Deps.androidxLifecycleViewmodelKtx)
    implementation(Deps.wearComposeMaterial)
    implementation(Deps.wearComposeFoundation)
    implementation(Deps.playservicesWearable)
}
