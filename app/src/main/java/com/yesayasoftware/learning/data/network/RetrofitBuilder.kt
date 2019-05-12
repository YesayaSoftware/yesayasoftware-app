package com.yesayasoftware.learning.data.network

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yesayasoftware.learning.BuildConfig
import com.yesayasoftware.learning.data.network.connectivity.ConnectivityInterceptor
import com.yesayasoftware.learning.internal.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://dev.yesaya.software/api/"

class RetrofitBuilder(
    var connectivityInterceptor: ConnectivityInterceptor
)   {
    companion object{
        private val client = buildClient()
        private val retrofit = buildRetrofit(client)

        private fun buildRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()

                    val builder = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Connection", "close")

                    request = builder.build()

                    chain.proceed(request)
                }

            if (BuildConfig.DEBUG)
                builder.addNetworkInterceptor(StethoInterceptor())

            return builder.build()
        }

        fun <T> createService(service: Class<T>): T {
            return retrofit.create(service)
        }

        fun getRetrofits(): Retrofit {
            return retrofit
        }
    }
    
    fun <T> createServiceWithAuth(service: Class<T>, tokenManager: TokenManager): T {
        Log.w("SERVICE", "onResponse: " + tokenManager.token.access_token.toString())

        val newClient = RetrofitBuilder.client.newBuilder().addInterceptor { chain ->
            var request = chain.request()

            val builder = request.newBuilder()

            if (tokenManager.token.access_token != null)
                builder.addHeader("Authorization", "Bearer " + tokenManager.token.access_token!!)

            request = builder.build()
            chain.proceed(request)
        }.authenticator(CustomAuthenticator.getInstance(tokenManager)).build()

        val newRetrofit = RetrofitBuilder.retrofit.newBuilder().client(newClient).build()

        return newRetrofit.create(service)
    }
}