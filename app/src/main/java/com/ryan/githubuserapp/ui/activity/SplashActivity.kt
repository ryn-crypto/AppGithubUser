package com.ryan.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.ryan.githubuserapp.R
import com.ryan.githubuserapp.data.datastore.ThemeSettings
import com.ryan.githubuserapp.data.datastore.dataStore
import com.ryan.githubuserapp.data.viewmodel.ThemeViewModel
import com.ryan.githubuserapp.data.viewmodel.ViewModelFactory
import com.ryan.githubuserapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = binding.splashTextView
        val fallAnimation = AnimationUtils.loadAnimation(this, R.anim.fall_in)
        fallAnimation.duration = 1000
        fallAnimation.interpolator = OvershootInterpolator()

        textView.startAnimation(fallAnimation)

        fallAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                textView.visibility = View.VISIBLE
            }
        })

        val themeSettings = ThemeSettings.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(themeSettings)
        themeViewModel = ViewModelProvider(this, viewModelFactory)[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}