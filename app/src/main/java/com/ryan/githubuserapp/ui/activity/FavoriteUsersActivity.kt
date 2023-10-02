package com.ryan.githubuserapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ryan.githubuserapp.data.database.FavoriteUser
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.databinding.ActivityFavoriteUsersBinding
import com.ryan.githubuserapp.ui.fragment.FavoriteUserFragment

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.title = "User Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val FUFragment = FavoriteUserFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, FUFragment)
        transaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Kembali ke MainActivity
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}