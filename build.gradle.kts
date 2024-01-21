buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(libs.secrets.gradle.plugin)
  }
}

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.hilt.android) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.gradle.secrets) apply false
}

