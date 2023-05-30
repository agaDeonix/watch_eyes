plugins {
    id(BuildPlugins.library)
    id(BuildPlugins.android)
}

android {
    namespace = Configs.namespace
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk

        testInstrumentationRunner = Configs.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    @Suppress("UnstableApiUsage")
    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)

    implementation(Deps.Google.material)

    implementation(Deps.Compose.animation)
    implementation(Deps.Compose.animationCore)
    implementation(Deps.Compose.uiTooling)
    implementation(Deps.Compose.foundation)

    implementation(Deps.KotlinX.Coroutines.core)
    implementation(Deps.KotlinX.Coroutines.android)

    implementation(Deps.Wear.Compose.material)
    implementation(Deps.Wear.Compose.foundation)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.extJUnit)
    androidTestImplementation(Deps.Test.espresso)
}
