package com.sewon.healthmonitor.ui.agreement

import androidx.compose.material.icons.Icons
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AgreementViewModel : ViewModel() {

    val MyAppIcons = Icons.Rounded
//    SomeComposable(icon = MyAppIcons.Menu)

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Test"
    }
    val text: LiveData<String> = _text

    private val _text2 = MutableLiveData<String>().apply {
        value = "This is dashboard Test2222"
    }

    val text2: LiveData<String> = _text2
}