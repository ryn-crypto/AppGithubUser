package com.ryan.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryan.githubuserapp.data.response.UserListResponse
import com.ryan.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel : ViewModel() {
    private val _originalUserList = MutableLiveData<List<UserListResponse>>()
    private val _userList = MutableLiveData<List<UserListResponse>>()
    val userList: LiveData<List<UserListResponse>> = _userList

    fun fetchUserList(username: String, type: String) {
        val client = ApiConfig.getApiService().getUsers(username, type)
        client.enqueue(object : Callback<List<UserListResponse>> {
            override fun onResponse(
                call: Call<List<UserListResponse>>,
                response: Response<List<UserListResponse>>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()
                    _originalUserList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserListResponse>>, t: Throwable) {
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
