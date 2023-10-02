package com.ryan.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserListResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
