package com.ryan.githubuserapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryan.githubuserapp.data.database.FavoriteUser
import com.ryan.githubuserapp.data.repository.FavoriteUserRepository
import com.ryan.githubuserapp.data.response.UserListResponse

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val fUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    val favoriteUsers: LiveData<List<FavoriteUser>> = fUserRepository.getAll()

    fun insert(user: FavoriteUser) {
        fUserRepository.insert(user)
    }
    fun update(user: FavoriteUser) {
        fUserRepository.update(user)
    }
    fun delete(user: FavoriteUser) {
        fUserRepository.delete(user)
    }
}