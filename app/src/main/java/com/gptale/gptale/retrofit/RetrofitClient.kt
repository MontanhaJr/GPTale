package com.gptale.gptale.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = "https://gptale-java-production.up.railway.app/"

        fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            http.readTimeout(60, TimeUnit.SECONDS)
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }
    }

    fun <T> createService(service: Class<T>): T {
        return getRetrofitInstance().create(service)
    }
}