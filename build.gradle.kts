buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(com.pouyaheydari.android.buildsrc.Libs.Android.gradlePlugin)
        classpath(com.pouyaheydari.android.buildsrc.Libs.Kotlin.gradlePlugin)
        classpath(com.pouyaheydari.android.buildsrc.Libs.Hilt.plugin)
        classpath(com.pouyaheydari.android.buildsrc.Libs.AndroidX.Navigation.safeArgs)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}