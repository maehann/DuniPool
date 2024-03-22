package com.example.dunipool.apiManager

import ir.dunijet.dunipool.apiManager.model.CoinsData
import ir.dunijet.dunipool.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIInterface {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder") queryParam:String = "popular"
    ):Call<NewsData>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoin(
        @Query("tsym") to_symbol :String = "USD" ,
        @Query("limit") limit_data :Int = 10
    ):Call<CoinsData>
}