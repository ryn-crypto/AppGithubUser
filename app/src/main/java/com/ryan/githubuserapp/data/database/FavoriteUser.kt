package com.ryan.githubuserapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null
) : Parcelable