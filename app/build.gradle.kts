plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ir.heydarii.musicmanager"
        minSdk = 21
        targetSdk = 30
        versionCode = 500
        versionName = "5.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.incremental" to "true",
                )
            }
        }
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

    // Support
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.retrofit2:converter-moshi:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    // Moshi
    implementation("com.squareup.moshi:moshi:${rootProject.extra["moshi_version"]}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${rootProject.extra["moshi_version"]}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${rootProject.extra["glide_version"]}")
    kapt("com.github.bumptech.glide:compiler:${rootProject.extra["glide_version"]}")

    // Lottie
    implementation("com.airbnb.android:lottie:3.7.1")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    kapt("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

    // Navigation component
    implementation("androidx.navigation:navigation-runtime-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["nav_version"]}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")

    // Paging3
    implementation("androidx.paging:paging-runtime:${rootProject.extra["paging_version"]}")

}

kapt {
    correctErrorTypes = true
}