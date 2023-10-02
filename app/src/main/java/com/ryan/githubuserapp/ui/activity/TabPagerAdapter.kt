package com.ryan.githubuserapp.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ryan.githubuserapp.ui.fragment.UserListFragment

class TabPagerAdapter(fragmentActivity: FragmentActivity, private val username: String) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = UserListFragment.newInstance(username, "followers")
                fragment
            }
            1 -> {
                val fragment = UserListFragment.newInstance(username, "following")
                fragment
            }
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }
}
