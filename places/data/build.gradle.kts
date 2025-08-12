plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.example.data"
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
    // Domain module dependency
    implementation(project(Modules.placesDomain))
    implementation(project(Modules.coreData))
    implementation(project(Modules.errors))

    // Core dependencies
    implementation(Core.coreKtx)
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Room Database
    implementation(Room.runtime)
    implementation(Room.ktx)
    ksp(Room.compiler)

    // Retrofit for API calls
    implementation(Retrofit.core)
    implementation(Retrofit.converterGson)

    // Hilt for dependency injection
    implementation(Hilt.android)
    ksp(Hilt.compiler)

    // Testing
    testImplementation(Test.junit)
    androidTestImplementation(Test.androidxJunit)
    androidTestImplementation(Test.espressoCore)
}