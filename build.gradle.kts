buildscript {
    val rxjava_Version by extra("2.2.13")
    val lifecycle_version by extra("2.3.1")
    val appcompat_version by extra("1.3.1")
    val retrofit_version by extra("2.9.0")
    val dagger_version by extra("2.38.1")
    val room_version by extra("2.3.0")
    val kotlin_version by extra("1.5.10")
    val nav_version by extra("2.3.5")
    val hilt_version by extra("2.37")
    val coroutines_version by extra("1.5.1")
    val glide_version by extra("4.12.0")
    val moshi_version by extra("1.12.0")
    val paging_version by extra("3.0.0")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}