object Dependency  {
    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21"

    object Configs {
        const val group = "org.jetbrains"
        const val module = "annotations-java5"
    }

    object Hilt {
        val ANDROID = "com.google.dagger:hilt-android:2.38.1"
        val COMPILER = "com.google.dagger:hilt-android-compiler:2.38.1"
        val WORKER = "androidx.hilt:hilt-work:1.0.0-alpha01"
    }

    object Room {
        const val RUNTIME = "androidx.room:room-runtime:2.4.0-alpha04"
        const val KTX = "androidx.room:room-ktx:2.4.0-alpha04"
        const val COMPILER ="androidx.room:room-compiler:2.4.0-alpha04"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:1.6.0"
    }

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.4.2"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:2.3.5"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:2.3.5"
        const val WORKER = "androidx.work:work-runtime-ktx:2.7.1"
    }

    object Lifecycle {
        const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
        const val EXT = "androidx.lifecycle:lifecycle-extensions:2.2.0"
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