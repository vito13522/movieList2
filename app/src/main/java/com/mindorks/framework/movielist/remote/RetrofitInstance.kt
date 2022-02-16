//package com.mindorks.framework.retrofitcoctail.remote
//
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
//
//object RetrofitInstance {
//
//    private val client = OkHttpClient.Builder().build()
//
//    private val retrofit by lazy{
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api:ApiInterface by lazy {
//        retrofit.create(ApiInterface::class.java)
//    }
//
//}