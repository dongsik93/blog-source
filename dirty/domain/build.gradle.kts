plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 26
        targetSdk = 32

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))

    implementation (Dependency.KTX.CORE)
    implementation (Dependency.AndroidX.APPCOMPAT)
    implementation (Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation (Dependency.AndroidX.NAVIGATION_FRAGMENT)
    implementation (Dependency.AndroidX.NAVIGATION_UI)
    implementation (Dependency.Material.MATERIAL)

    implementation (Dependency.Hilt.ANDROID)

    kapt(Dependency.Hilt.COMPILER)
    kaptAndroidTest (Dependency.Hilt.COMPILER)

    testImplementation (Dependency.TEST.JUNIT)
    androidTestImplementation (Dependency.TEST.JUNIT_EXT)
    androidTestImplementation (Dependency.TEST.ESPRESSO)
}