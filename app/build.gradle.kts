plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}
kotlin{
    jvmToolchain(11)
}
android {
    namespace = "com.example.spotq"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.example.spotq"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Module dependencies
    implementation(project(Modules.splash))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.onboarding))
    implementation(project(Modules.location_provider))

    // Authentication modules
    implementation(project(Modules.authenticationUi))
    implementation(project(Modules.authenticationDomain))
    implementation(project(Modules.authenticationData))

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

    // Serialization for type-safe navigation
    implementation(Serialization.json)

    // Dependency Injection - Hilt
    implementation(Hilt.android)
    ksp(Hilt.compiler)

    // Room Database
    implementation(Room.runtime)
    implementation(Room.ktx)
    ksp(Room.compiler)

    // Network - Retrofit
    implementation(Retrofit.core)
    implementation(Retrofit.converterGson)

    // Image Loading - Coil
    implementation(Coil.compose)

    // Testing libraries
    testImplementation(Test.junit)
    testImplementation(Test.mockk)
    androidTestImplementation(Test.androidxJunit)
    androidTestImplementation(Test.espressoCore)
    androidTestImplementation(platform(Compose.bom))
    androidTestImplementation(Compose.uiTestJunit4)

    // Debug libraries
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)
}