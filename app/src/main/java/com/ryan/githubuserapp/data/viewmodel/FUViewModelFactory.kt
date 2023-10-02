package com.ryan.githubuserapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FUViewModelFactory private constructor(private val pref: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FUViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): FUViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FUViewModelFactory::class.java) {
                    INSTANCE = FUViewModelFactory(application)
                }
            }
            return INSTANCE as FUViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}