plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version Versions.kotlin
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.example.main_navigation"
    compileSdk = Versions.compileSdk

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = Versions.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    // Core UI module
    implementation(project(Modules.coreUi))
    implementation(project(Modules.placesUi))
    implementation(project(Modules.favoritesUi))
    implementation(project(Modules.authenticationUi))
    implementation(project(Modules.errors))
    implementation(project(Modules.coreDomain))

    // Core Android libraries
    implementation(Core.coreKtx)
    implementation(Core.splashScreen)
    implementation(Lifecycle.runtimeKtx)

    // ViewModel and Lifecycle for Hilt integration
    implementation(Lifecycle.viewModelKtx)
    implementation(Lifecycle.viewModelCompose)
    implementation(Hilt.navigationCompose)

    // Compose BOM and UI libraries
    implementation(platform(Compose.bom))
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material3)
    implementation(Compose.materialIconsExtended)
    implementation(Compose.activity)
    implementation(Compose.navigation)


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