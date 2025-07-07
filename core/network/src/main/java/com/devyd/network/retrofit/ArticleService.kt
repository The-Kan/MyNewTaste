package com.devyd.network.retrofit

import com.devyd.data.models.ArticleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {
    @GET("articles")
    suspend fun getArticles(): List<ArticleDto>

    @GET("posts/{id}")
    suspend fun getPostSuspend(@Path("id") id: Int): Response<Post>
}

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)