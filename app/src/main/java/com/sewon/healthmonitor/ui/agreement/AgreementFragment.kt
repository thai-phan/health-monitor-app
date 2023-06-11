package com.sewon.healthmonitor.ui.agreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.databinding.FragmentDashboardBinding

class AgreementFragment : PreferenceFragmentCompat ()  {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey);
    }


}