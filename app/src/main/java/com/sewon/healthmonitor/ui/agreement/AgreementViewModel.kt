package com.sewon.healthmonitor.ui.agreement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AgreementViewModel : ViewModel() {

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