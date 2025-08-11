plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.spotq.core_ui"
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
    // Compose BOM for version alignment
    implementation(platform(Compose.bom))

    // Core Android libraries
    implementation(Core.coreKtx)

    // ViewModel and Lifecycle (needed for BaseViewModel)
    implementation(Lifecycle.viewModelKtx)
    implementation(Lifecycle.runtimeKtx)
    implementation(Lifecycle.viewModelCompose)

    // Coroutines (needed for StateFlow, Channel, etc.)
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Compose UI libraries
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material3)

    // Debug dependencies for Compose tooling
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)

    // Testing
    testImplementation(Test.junit)
}
