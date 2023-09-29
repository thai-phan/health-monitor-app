plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp") version ("1.8.20-1.0.11")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.sewon.healthmonitor"
  compileSdk = 34
  defaultConfig {
    applicationId = "com.sewon.healthmonitor"
    minSdk = 29
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments += ["room.schemaLocation": "$projectDir/schemas".toString()]
//            }
//        }
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.6"
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    buildConfig = true
    viewBinding = true
    compose = true
  }
//    androidResources {
//        generateLocaleConfig true
//    }

}

dependencies {
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.annotation)
  implementation(libs.androidx.core.i18n)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.constraintlayout.compose)
  implementation(libs.androidx.lifecycle.livedata.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.preference.ktx)
  implementation(libs.accompanist.permissions)


  // Http
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)
  implementation(libs.converter.gson)
  implementation(libs.retrofit)

  // Hilt dependency injection
  implementation(libs.hilt.android)
  // kapt "com.google.dagger:hilt-compiler:2.46.1")
  kapt(libs.androidx.hilt.compiler)
  kapt(libs.hilt.android.compiler)

  implementation(libs.hilt.navigation.compose)


  // Algorithm
  //
  implementation(libs.apache.commons.math3)


  // Database
  //
  implementation(libs.androidx.room.runtime)
  // annotationProcessor "androidx.room:room-compiler:$room_version")
  implementation(libs.androidx.room.ktx)
  // To use Kotlin Symbol Processing (KSP)
  ksp(libs.androidx.room.compiler)
  // optional - RxJava3 support for Room
  implementation(libs.androidx.room.rxjava3)
  // optional - Guava support for Room, including Optional and ListenableFuture
  implementation(libs.androidx.room.guava)
  // optional - Test helpers
  testImplementation(libs.androidx.room.testing)
  // optional - Paging 3 Integration
  implementation(libs.androidx.room.paging)

  // DataStore
  //
  // Preferences DataStore (SharedPreferences like APIs)
  implementation(libs.androidx.datastore.preferences)
  // optional - RxJava3 support
  implementation(libs.androidx.datastore.preferences.rxjava3)

  // UI Compose
  //
  implementation(libs.androidx.compiler)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.animation)
  implementation(libs.androidx.foundation)
  implementation(libs.androidx.foundation.layout)
  implementation(libs.androidx.material.icons.core)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.androidx.runtime)
  implementation(libs.androidx.runtime.livedata)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.util)
  implementation(libs.androidx.ui.text)
  implementation(libs.androidx.ui.tooling)
  implementation(libs.ui.tooling.preview)
  implementation(libs.androidx.ui.viewbinding)
  debugImplementation(libs.androidx.ui.tooling)

  //    implementation("nl.joery.timerangepicker:timerangepicker:1.0.0")

  // Chart
  //
  // Includes the core logic for charts and other elements.
  implementation(libs.core)
  // For Jetpack Compose.
  implementation(libs.compose)
  // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
  implementation(libs.compose.m3)


  // Log
  //
  implementation(libs.timber)

  implementation(libs.leakcanary.android)

  // Test
  //
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
  correctErrorTypes = true
}
