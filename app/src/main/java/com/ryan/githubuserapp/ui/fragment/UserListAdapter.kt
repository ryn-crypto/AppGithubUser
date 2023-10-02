package com.ryan.githubuserapp.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.ryan.githubuserapp.R
import com.ryan.githubuserapp.data.response.UserListResponse
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.databinding.ListItemUserBinding
import com.ryan.githubuserapp.helper.toFavoriteUser
import com.ryan.githubuserapp.ui.activity.DetailUserActivity
import com.ryan.githubuserapp.ui.activity.MainActivity

class UserListAdapter(private val favoriteUserViewModel: FavoriteUserViewModel) :
    ListAdapter<UserListResponse, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserListResponse) {
            binding.apply {

                val userRole = "GitHub ${user.type}"
                tvUsername.text = user.login
                tvRole.text = userRole

                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPhoto)

                when (val context = itemView.context) {
                    is MainActivity -> {
                        favoriteUserViewModel.favoriteUsers.observe(context) { favoriteUsers ->
                            val isFavorite = favoriteUsers.any { it.login == user.login }
                            val favoriteImageResource =
                                if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24

                            ivFavorite.setImageResource(favoriteImageResource)

                            ivFavorite.setOnClickListener {
                                try {
                                    if (isFavorite) {
                                        favoriteUserViewModel.delete(user.toFavoriteUser())
                                        showSnackbar(
                                            ivFavorite,
                                            "${user.login} remove from favorites",
                                            R.drawable.baseline_heart_broken_24
                                        )
                                    } else {
                                        favoriteUserViewModel.insert(user.toFavoriteUser())
                                        showSnackbar(
                                            ivFavorite,
                                            "${user.login} add to favorites",
                                            R.drawable.baseline_favorite_24
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }

                    is DetailUserActivity -> {
                        favoriteUserViewModel.favoriteUsers.observe(context) { favoriteUsers ->
                            val isFavorite = favoriteUsers.any { it.login == user.login }
                            val favoriteImageResource =
                                if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24

                            ivFavorite.setImageResource(favoriteImageResource)

                            ivFavorite.setOnClickListener {
                                try {
                                    if (isFavorite) {
                                        favoriteUserViewModel.delete(user.toFavoriteUser())
                                        showSnackbar(
                                            ivFavorite,
                                            "${user.login} remove from favorites",
                                            R.drawable.baseline_heart_broken_24
                                        )
                                    } else {
                                        favoriteUserViewModel.insert(user.toFavoriteUser())
                                        showSnackbar(
                                            ivFavorite,
                                            "${user.login} add to favorites",
                                            R.drawable.baseline_favorite_24
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }

                    else -> {
                        // Handle other cases or provide a default behavior
                    }
                }

                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailUserActivity::class.java)
                    intent.putExtra(EXTRA_LOGIN, user.login)
                    context.startActivity(intent)
                }
            }
        }
    }

    private fun showSnackbar(view: View, message: String, iconResId: Int) {
        try {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            val layoutParams = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.setMargins(40, 0, 40, 200)
            snackbarView.layoutParams = layoutParams
            val snackbarText =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            snackbarText.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0)
            snackbarText.compoundDrawablePadding = 20
            snackbar.show()
        } catch (e: Exception) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            val snackbarText =
                snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            snackbarText.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0)
            snackbarText.compoundDrawablePadding = 20
            snackbar.show()
        }
    }


    companion object {
        private const val EXTRA_LOGIN = "extra_login"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserListResponse>() {
            override fun areItemsTheSame(
                oldItem: UserListResponse,
                newItem: UserListResponse
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: UserListResponse,
                newItem: UserListResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}