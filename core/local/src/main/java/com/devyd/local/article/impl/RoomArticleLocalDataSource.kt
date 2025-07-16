package com.devyd.local.article.impl

import com.devyd.data.datasource.ArticleLocalDataSource
import com.devyd.data.models.ArticlesDto
import com.devyd.local.article.dao.ArticlesDao
import com.devyd.local.article.models.toDto
import com.devyd.local.article.models.toEntities

class RoomArticleLocalDataSource(
    private val articlesDao: ArticlesDao
) : ArticleLocalDataSource {
    override suspend fun getArticlesDto(category: String): ArticlesDto? {
        val articlesWithArticleEntities =
            articlesDao.getArticlesWithArticles(category) ?: return null

        val now = System.currentTimeMillis()
        val elapsed = now - articlesWithArticleEntities.articlesEntity.savedAt

        val oneHourMillis = 60 * 60 * 1000
        return if (elapsed > oneHourMillis) null else articlesWithArticleEntities.toDto()
    }

    override suspend fun add(category: String, articlesDto: ArticlesDto) {
        val pair = articlesDto.toEntities(category = category)
        val articlesEntity = pair.first
        val articleEntityList = pair.second
        articlesDao.add(articlesEntity)
        articlesDao.add(articleEntityList)
    }
}