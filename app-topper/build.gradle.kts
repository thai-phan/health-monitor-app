plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt.android)
}

android {
  namespace = "com.sewon.topperhealth"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig {
    applicationId = "com.sewon.topperhealth"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//    javaCompileOptions {
//      annotationProcessorOptions {
//        argument("room.schemaLocation", "$projectDir/schemas")
//      }
//    }


//    javaCompileOptions {
//      annotationProcessorOptions {
//        arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
//      }
//    }
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
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
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
  implementation(libs.androidx.core.splashscreen)
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
  implementation(libs.accompanist.systemuicontroller)


  // Http
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)
  implementation(libs.converter.gson)
  implementation(libs.retrofit)

  // Hilt dependency injection
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)   // Hilt compiler
//  kapt(libs.androidx.hilt.compiler)
//  kapt(libs.hilt.android.compiler)

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

  // DataStore
  //
  // Preferences DataStore (SharedPreferences like APIs)
  implementation(libs.androidx.datastore.preferences)

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

//  implementation(libs.leakcanary.android)

  // Test
  //
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}
