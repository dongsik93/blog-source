plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.dirty"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1"
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

    kotlinOptions {
        val options = this
        options.jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

configurations.all {
    exclude(group = Dependency.Configs.group, module = Dependency.Configs.module)
}

dependencies {

    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependency.kotlinStdlibJdk8)
    implementation(Dependency.KTX.CORE)
    implementation(Dependency.AndroidX.APPCOMPAT)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Dependency.AndroidX.NAVIGATION_UI)
    implementation(Dependency.AndroidX.WORKER)
    implementation(Dependency.Material.MATERIAL)
    implementation(Dependency.Room.RUNTIME)
    implementation(Dependency.Room.KTX)
    kapt(Dependency.Room.COMPILER)

    implementation(Dependency.Lifecycle.RUNTIME)
    implementation(Dependency.Lifecycle.VIEWMODEL)
    implementation(Dependency.Lifecycle.EXT)

    implementation(Dependency.Hilt.ANDROID)
    kapt(Dependency.Hilt.COMPILER)
    kaptAndroidTest(Dependency.Hilt.COMPILER)
    implementation(Dependency.Hilt.WORKER)

    testImplementation(Dependency.TEST.JUNIT)
    androidTestImplementation(Dependency.TEST.JUNIT_EXT)
    androidTestImplementation(Dependency.TEST.ESPRESSO)
}