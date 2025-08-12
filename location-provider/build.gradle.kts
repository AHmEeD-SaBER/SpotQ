plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(11)
}
android {
    namespace = "com.example.location_provider"
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

    implementation(Core.coreKtx)
    testImplementation(libs.junit)
    // Testing libraries
    testImplementation(Test.junit)
    testImplementation(Test.mockk)
    androidTestImplementation(Test.androidxJunit)
    androidTestImplementation(Test.espressoCore)

    implementation(Location.location)
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Dependency Injection - Hilt
    implementation(Hilt.android)
    ksp(Hilt.compiler)
}