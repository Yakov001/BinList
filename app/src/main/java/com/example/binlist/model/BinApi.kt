package com.example.binlist.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path

interface BinApi {

    @POST("/{card}")
    suspend fun requestBin(@Path("card") card: String) : Response<CardResponse>

    companion object {
        var userApi: BinApi? = null
        fun getInstance() : BinApi {
            if (userApi == null) {
                userApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BinApi::class.java)
            }
            return userApi!!
        }
        private const val BASE_URL = "https://lookup.binlist.net"
    }
}