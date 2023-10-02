package com.ryan.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)
