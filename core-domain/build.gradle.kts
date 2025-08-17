plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version Versions.kotlin
    id("kotlin-parcelize")

}
kotlin {
    jvmToolchain(11)
}
android {
    namespace = "com.example.core_domain"
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
}

dependencies {

    // Core dependencies
    implementation(Core.coreKtx)
    implementation(Coroutines.core)
    implementation(Coroutines.android)


    // Testing
    testImplementation(Test.junit)
    // Serialization for type-safe navigation
    implementation(Serialization.json)
}