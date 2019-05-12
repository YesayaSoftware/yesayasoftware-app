package com.yesayasoftware.learning.data.network

import com.yesayasoftware.learning.data.db.entity.CategoryEntry
import com.yesayasoftware.learning.data.network.response.PostResponse
import com.yesayasoftware.learning.internal.AccessToken
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    fun register(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String): Call<AccessToken>

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("email") username: String, @Field("password") password: String): Call<AccessToken>

    @POST("social_auth")
    @FormUrlEncoded
    fun socialAuth(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("provider") provider: String,
        @Field("provider_user_id") providerUserId: String
    ): Call<AccessToken>

    @POST("refresh")
    @FormUrlEncoded
    fun refresh(@Field("refresh_token") refreshToken: String): Call<AccessToken>

    @GET("posts")
    fun getPostsAsync(): Deferred<PostResponse>

    @POST("categories")
    @FormUrlEncoded
    fun categories(@Field("name") name: String, @Field("description") description: String): Call<CategoryEntry>
}