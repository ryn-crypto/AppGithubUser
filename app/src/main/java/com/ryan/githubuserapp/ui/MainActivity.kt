package com.ryan.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.ryan.githubuserapp.R
import com.ryan.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var requestType: String = "followers"
    private val userGithub = "ryn-crypto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        findUser()
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
                val userListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as? UserListFragment
                if (newText != null) {
                    userListFragment?.updateSearchQuery(newText)
                }
                return true
            }
        })

        return true
    }


    private fun findUser() {
        val userListFragment = UserListFragment.newInstance(userGithub, requestType)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, userListFragment)
        transaction.commit()
    }
}