import com.pouyaheydari.android.buildsrc.Libs

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp").version("1.6.0-1.0.1")
}

android {
    compileSdk = 31
    buildToolsVersion = "32.0.0"

    defaultConfig {
        applicationId = "ir.heydarii.musicmanager"
        minSdk = 21
        targetSdk = 31
        versionCode = 500
        versionName = "5.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":core"))

    // Support
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.liveData)
    implementation(Libs.AndroidX.viewModel)

    // Ui
    implementation(Libs.Material.material)
    implementation(Libs.AndroidX.constraintLayout)

    //Test
    testImplementation(Libs.Test.JUnit.junit)
    androidTestImplementation(Libs.Test.Ext.junit)
    testImplementation(Libs.Coroutines.test)
    testImplementation(Libs.AndroidX.Room.test)
    androidTestImplementation(Libs.Test.rules)
    androidTestImplementation(Libs.Test.core)
    testImplementation(Libs.Test.arch)
    androidTestImplementation(Libs.Test.espressoCore)
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    testImplementation("org.mockito:mockito-core:4.2.0")
    androidTestImplementation("org.mockito:mockito-android:4.2.0")
    testImplementation("org.mockito:mockito-inline:4.2.0")

    // Retrofit
    implementation(Libs.Network.Retrofit.retrofit)
    implementation(Libs.Network.Retrofit.moshiConverter)
    implementation(Libs.Network.Retrofit.logging)

    // Coroutines
    implementation(Libs.Coroutines.android)

    // Moshi
    ksp(Libs.Network.Moshi.moshi)

    // Glide
    implementation(Libs.Glide.glide)
    kapt(Libs.Glide.compiler)

    // Lottie
    implementation(Libs.Lottie.lottie)

    // Room
    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.kotlinExtension)
    ksp(Libs.AndroidX.Room.compiler)

    // Navigation component
    implementation(Libs.AndroidX.Navigation.fragment)
    implementation(Libs.AndroidX.Navigation.ui)

    // Hilt
    implementation(Libs.Hilt.android)
    kapt(Libs.Hilt.compiler)

    // Paging3
    implementation(Libs.AndroidX.Paging.paging)

}

kapt {
    correctErrorTypes = true
}