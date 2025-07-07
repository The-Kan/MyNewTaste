package com.devyd.data.datasource

import com.devyd.data.models.ArticleDto

interface ArticleRemoteDataSource  {
    suspend fun fetchArticles(): List<ArticleDto>
}