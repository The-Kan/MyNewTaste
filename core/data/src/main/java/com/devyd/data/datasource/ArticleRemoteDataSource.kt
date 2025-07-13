package com.devyd.data.datasource

import com.devyd.data.models.ArticlesDto

interface ArticleRemoteDataSource  {
    suspend fun fetchNewsEntity(category: String): ArticlesDto
}