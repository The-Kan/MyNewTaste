package com.devyd.local.article.impl

import com.devyd.data.datasource.BookmarkedArticleLocalDataSource
import com.devyd.data.models.ArticleDto
import com.devyd.local.article.dao.BookMarkedArticleDao
import com.devyd.local.bookmarkedarticle.models.toEntity
import javax.inject.Inject

class RoomBookmarkedArticleLocalDataSource @Inject constructor(
    private val bookMarkedArticleDao: BookMarkedArticleDao
) : BookmarkedArticleLocalDataSource {
    override suspend fun bookmark(articleDto: ArticleDto): Long {
        return bookMarkedArticleDao.add(articleDto.toEntity())
    }

    override suspend fun unBookmark(articleDto: ArticleDto): Int {
        return bookMarkedArticleDao.deleteByTitleAndPublishedAt(
            articleDto.title,
            articleDto.publishedAt
        )
    }

    override suspend fun isBookmarked(articleDto: ArticleDto): Boolean {
        return bookMarkedArticleDao.isBookmarked(
            articleDto.title,
            articleDto.publishedAt
        )
    }
}