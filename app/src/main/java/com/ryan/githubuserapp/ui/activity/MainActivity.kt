package com.ryan.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.ryan.githubuserapp.R
import com.ryan.githubuserapp.data.database.AppDatabase
import com.ryan.githubuserapp.data.datastore.ThemeSettings
import com.ryan.githubuserapp.data.datastore.dataStore
import com.ryan.githubuserapp.data.viewmodel.ThemeViewModel
import com.ryan.githubuserapp.data.viewmodel.ViewModelFactory
import com.ryan.githubuserapp.databinding.ActivityMainBinding
import com.ryan.githubuserapp.ui.fragment.SearchUserFragment
import com.ryan.githubuserapp.ui.fragment.BottomSettingsFragment
import com.ryan.githubuserapp.ui.fragment.UserListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeViewModel: ThemeViewModel
    private var requestType: String = "followers"
    private val userGithub = "ryn-crypto"

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(
            resizeIcon(resources.getDrawable(R.drawable.mark), 30, 30)
        )

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

        alertRemove()
        findUser()

        val fab = binding.fab

        fab.setOnClickListener {
            val intent = Intent(this, FavoriteUsersActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchView = binding.topAppBar.menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    val searchUserFragment = SearchUserFragment.newInstance(query)
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.fragmentContainer.id, searchUserFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val userListFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container) as? UserListFragment
                if (newText != null) {
                    userListFragment?.updateSearchQuery(newText)
                }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val bottomSheetFragment = BottomSettingsFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun alertRemove() {

        val aboveFabText = binding.aboveFabText

        Handler().postDelayed({
            val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            aboveFabText.startAnimation(fadeInAnimation)
            aboveFabText.visibility = View.VISIBLE
        }, 1500)

        Handler().postDelayed({
            val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    aboveFabText.visibility = View.GONE
                }
            })
            aboveFabText.startAnimation(fadeOutAnimation)
        }, 4000)
    }


    private fun findUser() {
        val userListFragment = UserListFragment.newInstance(userGithub, requestType)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, userListFragment)
        transaction.commit()
    }

    private fun resizeIcon(icon: Drawable, desiredWidth: Int, desiredHeight: Int): Drawable {
        val newIcon = BitmapDrawable(resources, Bitmap.createScaledBitmap(
            (icon as BitmapDrawable).bitmap,
            desiredWidth,
            desiredHeight,
            true
        ))
        newIcon.setBounds(0, 0, desiredWidth, desiredHeight)
        return newIcon
    }
}