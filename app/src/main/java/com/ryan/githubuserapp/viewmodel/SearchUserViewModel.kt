package com.ryan.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryan.githubuserapp.data.response.ItemsItem
import com.ryan.githubuserapp.data.response.UserSearchResponse
import com.ryan.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    fun searchUsers(query: String) {
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<UserSearchResponse> {

            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()?.items as List<ItemsItem>?
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}
