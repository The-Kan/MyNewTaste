package com.devyd.network.retrofit

import com.devyd.network.models.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getBusinessHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
       // @Query("apiKey") apiKey: String = "5a560d99ec304137978ecc18f3ececad"
        @Query("apiKey") apiKey: String = "1e1efe3e63de48b197d0fada9d5fd0a4"
    ): Response<ArticlesResponse>
}

