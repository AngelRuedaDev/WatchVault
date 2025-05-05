import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("kapt") // solo si decides usar kapt
    alias(libs.plugins.hilt) // si usas version catalog
}

fun getApiKeyFromEnv(): String {
    val envFile = rootProject.file(".env")
    if (envFile.exists()) {
        val props = Properties()
        envFile.inputStream().use { props.load(it) }
        return props.getProperty("TMDB_API_KEY") ?: ""
    }
    return ""
}

fun getTokenFromEnv(): String {
    val envFile = rootProject.file(".env")
    if (envFile.exists()) {
        val props = Properties()
        envFile.inputStream().use { props.load(it) }
        return props.getProperty("TMDB_ACCESS_TOKEN") ?: ""
    }
    return ""
}


android {
    namespace = "com.angelruedadev.watchvault"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.angelruedadev.watchvault"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "TMDB_API_KEY", "\"${getApiKeyFromEnv()}\"")
        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"${getTokenFromEnv()}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true //Uses the generation of BuildConfig
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //DaggerHilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}