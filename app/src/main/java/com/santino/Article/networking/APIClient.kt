package com.santino.Article.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://5e99a9b1bc561b0016af3540.mockapi.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass)
    }
}