object Dependencies {
    const val gradle = "com.android.tools.build:gradle:7.0.4"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:2.36"
    const val gmsGoogleService = "com.google.gms:google-services:4.3.10"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.9.0"
}

object Dependency  {
    object KTX {
        const val CORE = "androidx.core:core-ktx:1.7.0"
    }

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.4.2"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:2.3.5"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:2.3.5"
    }

    object Material {
        const val MATERIAL = "com.google.android.material:material:1.6.1"
    }

    object TEST {
        const val JUNIT = "junit:junit:4.13.2"
        const val JUNIT_EXT = "androidx.test.ext:junit:1.1.3"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:3.4.0"
    }
}