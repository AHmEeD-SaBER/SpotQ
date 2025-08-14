plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.example.ui"
    compileSdk = Versions.compileSdk

    buildFeatures {
        compose = true
    }

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    // Domain module dependency
    implementation(project(Modules.placesDomain))
    // Core UI module
    implementation(project(Modules.coreUi))
    implementation(project(Modules.location_provider))
    implementation(project(Modules.errors))

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