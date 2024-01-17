package com.sewon.topperhealth.ui.screen.device

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


data class UiState(
  val message: String = ""
)


@HiltViewModel
class DeviceScreenViewModel @Inject constructor(
) : ViewModel() {

  init {
  }

  override fun onCleared() {
    Timber.tag("Timber").d("onCleared")
  }


}