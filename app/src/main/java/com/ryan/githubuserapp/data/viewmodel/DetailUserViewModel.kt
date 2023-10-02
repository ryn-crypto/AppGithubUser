package com.ryan.githubuserapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryan.githubuserapp.data.response.UserDetailResponse
import com.ryan.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    fun loadUserDetails(login: String) {
        val client = ApiConfig.getApiService().getDetail(login)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        _userDetail.postValue(user!!)
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}