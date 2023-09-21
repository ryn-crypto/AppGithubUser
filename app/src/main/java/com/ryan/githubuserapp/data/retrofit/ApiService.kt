package com.ryan.githubuserapp.data.retrofit

import com.ryan.githubuserapp.data.response.UserListResponse
import com.ryan.githubuserapp.data.response.UserDetailResponse
import com.ryan.githubuserapp.data.response.UserSearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/users/{id}/{type}")
    fun getUsers(
        @Path("id") id: String,
        @Path("type") type: String
    ): Call<List<UserListResponse>>

    @GET("/users/{id}")
    fun getDetail(
        @Path("id") id: String
    ): Call<UserDetailResponse>

    @GET("/search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UserSearchResponse>
}