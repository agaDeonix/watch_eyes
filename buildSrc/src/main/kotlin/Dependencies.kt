object BuildPlugins {
    val application by lazy { "com.android.application:${Versions.androidGradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin.android:${Versions.kotlin}" }
}

object Deps {
    val androidDesugarjdklibs = "com.android.tools:desugar_jdk_libs:1.1.5"
    val androidxActivityCompose = "androidx.activity:activity-compose:${Versions.androidxActivity}"
    val androidxCoreKtx = "androidx.core:core-ktx:1.9.0"
    val androidxFragmentKtx = "androidx.fragment:fragment-ktx:1.5.2"
    val androidxLifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
    val androidxLifecycleViewmodelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidxLifecycle}"
    val androidxLifecycleViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    val composeCompiler = "androidx.compose.compiler:compiler:1.2.0"
    val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    val jacocoAnt = "org.jacoco:org.jacoco.ant:0.8.8"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlinxCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx}"
    val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
    val kotlinxCoroutinesPlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinx}"
    val playservicesWearable = "com.google.android.gms:play-services-wearable:17.1.0"
    val wearComposeFoundation =
        "androidx.wear.compose:compose-foundation:${Versions.androidxWearCompose}"
    val wearComposeMaterial =
        "androidx.wear.compose:compose-material:${Versions.androidxWearCompose}"
}
