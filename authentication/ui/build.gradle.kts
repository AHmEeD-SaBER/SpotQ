plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.spotq.authentication.ui"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    // Domain module dependency
    implementation(project(":authentication:domain"))
    // Core UI module
    implementation(project(":core-ui"))

    // Core dependencies
    implementation(Core.coreKtx)
    implementation(Lifecycle.runtimeKtx)
    implementation(Lifecycle.viewModelCompose)

    // Compose dependencies
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material3)
    implementation(Compose.materialIconsExtended)
    implementation(Compose.activity)

    // Hilt
    implementation(Hilt.android)
    ksp(Hilt.ksp)
    implementation(Hilt.navigationCompose)

    // Testing
    testImplementation(Test.junit)
    androidTestImplementation(Test.androidxJunit)
    androidTestImplementation(Test.espressoCore)
    androidTestImplementation(Compose.uiTestJunit4)
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)
}
