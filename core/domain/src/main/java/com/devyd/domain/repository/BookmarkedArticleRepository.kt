package com.devyd.domain.repository

import com.devyd.domain.models.Article
import com.devyd.domain.models.Articles

interface BookmarkedArticleRepository {
    suspend fun bookmark(article: Article): Long
    suspend fun unBookmark(article: Article): Int
    suspend fun isBookMarked(article: Article): Boolean
    suspend fun getArticles(): Articles
}