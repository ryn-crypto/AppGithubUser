package com.ryan.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ryan.githubuserapp.data.database.AppDatabase
import com.ryan.githubuserapp.data.database.FavoriteUser
import com.ryan.githubuserapp.data.database.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application) {
    private val fUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = AppDatabase.getDatabase(application)
        fUserDao = db.favoriteUserDao()
    }
    fun getAll(): LiveData<List<FavoriteUser>> = fUserDao.getAll()
    fun insert(user: FavoriteUser) {
        executorService.execute { fUserDao.insert(user) }
    }
    fun delete(user: FavoriteUser) {
        executorService.execute { fUserDao.delete(user) }
    }
    fun update(user: FavoriteUser) {
        executorService.execute { fUserDao.update(user) }
    }
}