import com.pouyaheydari.android.buildsrc.Libs

plugins {
    id ("kotlin")
}

dependencies {
    implementation(Libs.JavaX.inject)
    implementation(Libs.Coroutines.core)
}