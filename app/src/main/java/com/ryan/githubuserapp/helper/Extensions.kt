package com.ryan.githubuserapp.helper

import com.ryan.githubuserapp.data.database.FavoriteUser
import com.ryan.githubuserapp.data.response.ItemsItem
import com.ryan.githubuserapp.data.response.UserDetailResponse
import com.ryan.githubuserapp.data.response.UserListResponse

fun UserListResponse.toFavoriteUser(): FavoriteUser {
    return FavoriteUser(
        id = id,
        login = login,
        type = type,
        avatarUrl = avatarUrl
    )
}

fun List<ItemsItem>.toUserListResponseList(): List<UserListResponse> {
    return map { itemsItem ->
        UserListResponse(
            id = itemsItem.id,
            login = itemsItem.login,
            type = itemsItem.type,
            avatarUrl = itemsItem.avatarUrl
        )
    }
}

fun UserDetailResponse.toFavoriteUser(): FavoriteUser {
    return FavoriteUser(
        id = id,
        login = login,
        type = type,
        avatarUrl = avatarUrl
    )
}