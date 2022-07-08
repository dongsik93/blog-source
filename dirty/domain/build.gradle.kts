plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 26
        targetSdk = 31

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
                arguments["room.incremental"] = "true"
                arguments["room.expandProjection"] = "true"
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    /* ONLY Hilt / Coroutine Test*/
    packagingOptions {
        resources {
            excludes += "/META-INF/AL2.0"
            excludes += "/META-INF/LGPL2.1"
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
}

configurations.all {
    exclude(group = Dependency.Configs.group, module = Dependency.Configs.module)
}


dependencies {

    implementation (Dependency.kotlinStdlibJdk8)
    implementation (Dependency.KTX.CORE)

    implementation(Dependency.Room.RUNTIME)
    implementation(Dependency.Room.KTX)
    kapt(Dependency.Room.COMPILER)

    implementation (Dependency.Hilt.ANDROID)
    kapt(Dependency.Hilt.COMPILER)
    kaptAndroidTest (Dependency.Hilt.COMPILER)
}