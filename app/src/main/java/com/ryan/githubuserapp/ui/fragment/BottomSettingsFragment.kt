package com.ryan.githubuserapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ryan.githubuserapp.data.datastore.ThemeSettings
import com.ryan.githubuserapp.data.datastore.dataStore
import com.ryan.githubuserapp.databinding.FragmentBottomSettingsBinding
import com.ryan.githubuserapp.data.viewmodel.ThemeViewModel
import com.ryan.githubuserapp.data.viewmodel.ViewModelFactory


class BottomSettingsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchDarkMode = binding.switchMode

        val themeSettings = ThemeSettings.getInstance(requireContext().dataStore)

        val themeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(themeSettings)
        )[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                switchDarkMode.text = "Light Mode"
                switchDarkMode.isChecked = true
            } else {
                switchDarkMode.text = "Dark Mode"
                switchDarkMode.isChecked = false
            }
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
