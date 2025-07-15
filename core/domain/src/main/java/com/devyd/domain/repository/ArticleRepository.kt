package com.devyd.domain.repository

import com.devyd.domain.models.Articles

interface ArticleRepository {
    suspend fun getArticles(category: String): Articles
}