package com.ryan.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Update
    fun update(user : FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY id ASC")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Delete
    fun delete(user: FavoriteUser)
}