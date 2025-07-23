package com.devyd.data.datasource

import com.devyd.data.models.ArticleDto
import com.devyd.data.models.ArticlesDto

interface BookmarkedArticleLocalDataSource {
    suspend fun bookmark(articleDto: ArticleDto): Long
    suspend fun unBookmark(articleDto: ArticleDto): Int
    suspend fun isBookmarked(articleDto: ArticleDto): Boolean
    suspend fun getArticlesDto(): ArticlesDto
}