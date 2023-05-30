object BuildPlugins {
    val jvm = "org.jetbrains.kotlin.jvm"
    val library = "com.android.library"
    val application = "com.android.application"
    val android = "org.jetbrains.kotlin.android"
}

object Deps {

    val androidDesugarjdklibs = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkLibs}"

    val jacocoAnt = "org.jacoco:org.jacoco.ant:${Versions.jacocoAnt}"

    val classpathKotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    object StdLib {
        val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlibJdk}"
        val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.stdlibJdk}"
    }

    object Google {
        val accompanistPager =
            "com.google.accompanist:accompanist-pager:${Versions.accompanistPager}"
        val material = "com.google.android.material:material:${Versions.googleMaterial}"
        val playservicesWearable =
            "com.google.android.gms:play-services-wearable:${Versions.playServicesWearable}"
    }

    object AndroidX {
        val navigation = "androidx.navigation:navigation-compose:${Versions.androidxNavigation}"
        val appcompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
        val activityCompose = "androidx.activity:activity-compose:${Versions.androidxActivity}"
        val coreKtx = "androidx.core:core-ktx:${Versions.androidxCoreKtx}"
        val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.androidxFragmentKtx}"
        val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
        val lifecycleViewmodelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidxLifecycle}"
        val lifecycleViewmodelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    }

    object Compose {
        val animationCore = "androidx.compose.animation:animation-core:${Versions.composeAnimation}"
        val animation = "androidx.compose.animation:animation:${Versions.composeAnimation}"
        val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
        val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        val material = "androidx.compose.material:material:${Versions.compose}"
        val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    }

    object KotlinX {
        object Coroutines {
            val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx}"
            val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
            val playServices =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinx}"
        }
    }

    object Wear {
        object Compose {
            val foundation =
                "androidx.wear.compose:compose-foundation:${Versions.androidxWearCompose}"
            val material =
                "androidx.wear.compose:compose-material:${Versions.androidxWearCompose}"
        }
    }

    object Test {
        val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        val extJUnit = "androidx.test.ext:junit:${Versions.extJUnit}"
        val junit = "junit:junit:${Versions.junit}"
    }

}
