package com.ryan.githubuserapp.ui.activity

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
import com.ryan.githubuserapp.data.database.FavoriteUser
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.databinding.ListItemUserBinding

class FavoriteUserListAdapter(private val favoriteUserViewModel: FavoriteUserViewModel) :
    ListAdapter<FavoriteUser, FavoriteUserListAdapter.FavoriteUserViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class FavoriteUserViewHolder(private val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: FavoriteUser) {
            binding.apply {

                val userRole = "GitHub ${user.type}"
                tvUsername.text = user.login
                tvRole.text = userRole

                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPhoto)

                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailUserActivity::class.java)
                    intent.putExtra(EXTRA_LOGIN, user.login)
                    context.startActivity(intent)
                }

                ivFavorite.setOnClickListener {
                    favoriteUserViewModel.favoriteUsers.observe(itemView.context as FavoriteUsersActivity) {
                        ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                    showSnackbar(
                        ivFavorite,
                        "${user.login} remove from favorites",
                        R.drawable.baseline_heart_broken_24
                    )
                    favoriteUserViewModel.delete(user)
                }
            }
        }
    }

    private fun showSnackbar(view: View, message: String, iconResId: Int) {
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
    }

    companion object {
        private const val EXTRA_LOGIN = "extra_login"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}