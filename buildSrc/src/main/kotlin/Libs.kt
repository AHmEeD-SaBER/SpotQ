object Core {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.junitVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
}

object Lifecycle {
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleRuntimeKtx}"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleRuntimeKtx}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Compose {
    const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val ui = "androidx.compose.ui:ui"
    const val uiGraphics = "androidx.compose.ui:ui-graphics"
    const val uiTooling = "androidx.compose.ui:ui-tooling"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
    const val material3 = "androidx.compose.material3:material3"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.materialIcons}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ksp = "androidx.room:room-compiler:${Versions.room}"
}

object Hilt {
    const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val ksp = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val navigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
}

object Retrofit {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Coil {
    const val compose = "io.coil-kt:coil-compose:${Versions.coil}"
}

object Serialization {
    const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
}
