package com.ryan.githubuserapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "favoriteUsers")
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }

}