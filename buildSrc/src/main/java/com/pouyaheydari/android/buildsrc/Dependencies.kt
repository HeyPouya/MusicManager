package com.pouyaheydari.android.buildsrc

object Libs {

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.0.4"
    }

    object Kotlin {
        private const val version = "1.6.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie:4.2.2"
    }

    object Material {
        const val material = "com.google.android.material:material:1.4.0"
    }

    object Test {
        private const val version = "1.4.0"
        const val core = "androidx.test:core:$version"
        const val rules = "androidx.test:rules:$version"
        const val arch = "androidx.arch.core:core-testing:2.1.0"

        object JUnit {
            const val junit = "junit:junit:4.13.2"
        }

        object Mockk {
            const val mockk = "io.mockk:mockk:1.12.1"
        }

        object Ext {
            const val junit = "androidx.test.ext:junit-ktx:1.1.3"
        }

        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

    object AndroidX {
        private const val lifecycleVersion = "2.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

        object Navigation {
            private const val version = "2.3.5"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Paging {
            private const val version = "3.1.0"
            const val paging = "androidx.paging:paging-runtime:$version"
            const val common = "androidx.paging:paging-common:$version"
        }

        object Room {
            private const val version = "2.4.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val kotlinExtension = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val test = "androidx.room:room-testing:$version"
        }

    }

    object Network {
        object Retrofit {
            private const val version = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
            const val logging = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3"
        }

        object Moshi {
            const val moshi = "com.squareup.moshi:moshi-kotlin-codegen:1.13.0"
        }
    }

    object Hilt {
        private const val version = "2.40.5"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }
}