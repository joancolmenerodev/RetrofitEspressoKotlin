package com.joancolmenerodev.retrofitespresso.networking

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class ApiClient {

    companion object {

        val baseURL: String = "http://poetrydb.org/"
        var retofit: Retrofit? = null

        val client: Retrofit
            get() {
                if (retofit == null) {
                    retofit = Retrofit.Builder()
                            .baseUrl(baseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retofit!!
            }
    }
}