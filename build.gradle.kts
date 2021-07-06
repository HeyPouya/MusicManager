buildscript {
    val rxjava_Version    by extra("2.2.13")
    val lifecycle_version by extra("2.3.1")
    val appcompat_version by extra("1.3.1")
    val support_version   by extra("1.4.0")
    val retrofit_version  by extra("2.9.0")
    val dagger_version    by extra("2.37")
    val room_version      by extra("2.3.0")
    val kotlin_version    by extra("1.5.20")
    val nav_version       by extra("2.3.5")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}