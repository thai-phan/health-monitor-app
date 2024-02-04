plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.gradle.secrets)
}

android {
  namespace = "com.sewon.topperhealth"
  compileSdk = 34
  defaultConfig {
    applicationId = "com.sewon.topperhealth"
    minSdk = 29
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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
  implementation(libs.retrofit.converter.scalars)
  implementation(libs.retrofit.converter.gson)
  implementation(libs.retrofit)

  // Hilt dependency injection
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)


  // Algorithm
  //
  implementation(libs.apache.commons.math3)


  // Database
  //
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  // DataStore
  //
  implementation(libs.androidx.datastore.preferences)

  // UI Compose
  //
  implementation(libs.androidx.compiler)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.animation)
  implementation(libs.androidx.foundation)
  implementation(libs.androidx.foundation.layout)
  implementation(libs.androidx.material.icons.core)
  implementation(libs.androidx.runtime)
  implementation(libs.androidx.runtime.livedata)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.util)
  implementation(libs.androidx.ui.text)
  implementation(libs.androidx.ui.tooling)
  implementation(libs.ui.tooling.preview)
  implementation(libs.androidx.ui.viewbinding)
  debugImplementation(libs.androidx.ui.tooling)


  // Chart
  //
  implementation(libs.vico.core)
  implementation(libs.vico.compose)
  implementation(libs.vico.compose.m3)


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

secrets {
  // Optionally specify a different file name containing your secrets.
  // The plugin defaults to "local.properties"
  propertiesFileName = "local.properties"

  // A properties file containing default secret values. This file can be
  // checked in version control.
  defaultPropertiesFileName = "local.properties"

  // Configure which keys should be ignored by the plugin by providing regular expressions.
  // "sdk.dir" is ignored by default.
  ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
  ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}
