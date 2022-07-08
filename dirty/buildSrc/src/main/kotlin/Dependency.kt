object Dependency  {
    object Hilt {
        val ANDROID = "com.google.dagger:hilt-android:2.42"
        val COMPILER = "com.google.dagger:hilt-android-compiler:2.42"
    }

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