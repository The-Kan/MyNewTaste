package com.devyd.data.datasource

import com.devyd.data.models.ArticlesDto

interface ArticleLocalDataSource {
    suspend fun getArticlesDto(category: String): ArticlesDto?
    suspend fun add(category: String, articlesDto: ArticlesDto)
}