// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Project.KOTLIN_PLUGIN)
        classpath(Project.GRADLE_PLUGIN)
        classpath(Project.HILT_PLUGIN)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}