plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

object Project {
    const val gradle = "com.android.tools.build:gradle:7.0.4"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:2.36"
    const val gmsGoogleService = "com.google.gms:google-services:4.3.10"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.9.0"
}

dependencies {
    implementation(Project.gradle)
    implementation(Project.kotlinPlugin)
    implementation(Project.hiltPlugin)
    implementation(Project.gmsGoogleService)
    implementation(Project.firebaseCrashlytics)
}