package com.ryan.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ryan.githubuserapp.data.response.UserListResponse
import com.ryan.githubuserapp.databinding.ListItemUserBinding

class UserListAdapter :
    ListAdapter<UserListResponse, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    private val EXTRA_LOGIN = "extra_login"

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

                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailUserActivity::class.java)
                    intent.putExtra(EXTRA_LOGIN, user.login)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
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