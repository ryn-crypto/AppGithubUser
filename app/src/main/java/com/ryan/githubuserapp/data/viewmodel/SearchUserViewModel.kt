package com.ryan.githubuserapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryan.githubuserapp.data.response.ItemsItem
import com.ryan.githubuserapp.data.response.UserListResponse
import com.ryan.githubuserapp.data.response.UserSearchResponse
import com.ryan.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {

    private val _originalUserList = MutableLiveData<List<ItemsItem>>()
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
                    _originalUserList.value = response.body()?.items as List<ItemsItem>?
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                // Handle error
            }
        })
    }

    fun queryUserListByCriteria(criteria: String) {
        val originalList = _originalUserList.value.orEmpty()
        val filteredList = originalList.filter { user ->
            user.login?.contains(criteria, ignoreCase = true) == true
        }
        _userList.value = filteredList
    }

    fun restoreOriginalUserList() {
        _userList.value = _originalUserList.value
    }
}
