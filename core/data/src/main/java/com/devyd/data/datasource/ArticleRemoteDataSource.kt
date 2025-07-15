package com.devyd.data.datasource

import com.devyd.data.models.ArticlesDto

interface ArticleRemoteDataSource  {
    suspend fun fetchArticlesDto(category: String): ArticlesDto
}