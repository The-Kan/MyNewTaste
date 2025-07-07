package com.devyd.domain.repository

import com.devyd.domain.models.Article

interface ArticleRepository {
    suspend fun getArticles(): List<Article>
}