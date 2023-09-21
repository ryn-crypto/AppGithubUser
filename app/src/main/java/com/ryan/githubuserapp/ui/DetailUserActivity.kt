package com.ryan.githubuserapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ryan.githubuserapp.data.response.UserDetailResponse
import com.ryan.githubuserapp.databinding.ActivityDetailUserBinding
import com.ryan.githubuserapp.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var login: String

    private lateinit var shimmerAvatar: ShimmerFrameLayout
    private lateinit var shimmerName: ShimmerFrameLayout
    private lateinit var shimmerBio: ShimmerFrameLayout
    private lateinit var shimmerPublic: ShimmerFrameLayout
    private lateinit var shimmerFollower: ShimmerFrameLayout
    private lateinit var shimmerFollowing: ShimmerFrameLayout

    companion object {
        private const val EXTRA_LOGIN = "extra_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        login = intent.getStringExtra(EXTRA_LOGIN) ?: ""

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        setupViews()
        startShimmerAnimations()
        setupTabLayoutAndViewPager()

        if (login.isNotEmpty()) {
            viewModel.loadUserDetails(login)
            val tabPagerAdapter = TabPagerAdapter(this, login)
            binding.viewPager.adapter = tabPagerAdapter
        }

        viewModel.userDetail.observe(this) { user ->
            displayUserDetails(user)
            stopShimmerAnimations()
            showLoading(false)
        }
    }

    private fun displayUserDetails(user: UserDetailResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAvatar)

            val name = "${user.name} (${user.login})"
            val repo = "${user.publicRepos}\nRepository"
            val follower = "${user.followers}\nFollower"
            val following = "${user.following}\nFollowing"

            tvName.text = name
            tvPublic.text = repo
            tvFollower.text = follower
            tvFollowing.text = following

            ivWebLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                startActivity(intent)
            }

            if (user.bio != null) {
                tvbio.text = user.bio
            } else {
                tvbio.visibility = View.GONE
            }
        }
    }

    private fun setupTabLayoutAndViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = TabPagerAdapter(this@DetailUserActivity, login)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Follower"
                1 -> tab.text = "Following"
            }
        }.attach()
    }

    private fun startShimmerAnimations() {
        shimmerAvatar.startShimmer()
        shimmerName.startShimmer()
        shimmerBio.startShimmer()
        shimmerPublic.startShimmer()
        shimmerFollower.startShimmer()
        shimmerFollowing.startShimmer()
    }

    private fun stopShimmerAnimations() {
        shimmerAvatar.stopShimmer()
        shimmerName.stopShimmer()
        shimmerBio.stopShimmer()
        shimmerPublic.stopShimmer()
        shimmerFollower.stopShimmer()
        shimmerFollowing.stopShimmer()

        shimmerAvatar.visibility = View.GONE
        shimmerName.visibility = View.GONE
        shimmerBio.visibility = View.GONE
        shimmerPublic.visibility = View.GONE
        shimmerFollower.visibility = View.GONE
        shimmerFollowing.visibility = View.GONE
    }

    private fun setupViews() {
        shimmerAvatar = binding.shimmerAvatar
        shimmerName = binding.shimmerName
        shimmerBio = binding.shimmerBio
        shimmerPublic = binding.shimmerPublic
        shimmerFollower = binding.shimmerFollower
        shimmerFollowing = binding.shimmerFollowing
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvName.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvPublic.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvFollower.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
