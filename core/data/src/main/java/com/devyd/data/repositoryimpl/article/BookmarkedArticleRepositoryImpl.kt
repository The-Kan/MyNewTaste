package com.devyd.data.repositoryimpl.article

import com.devyd.data.datasource.BookmarkedArticleLocalDataSource
import com.devyd.data.models.toDomain
import com.devyd.data.models.toDto
import com.devyd.domain.models.Article
import com.devyd.domain.models.Articles
import com.devyd.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class BookmarkedArticleRepositoryImpl @Inject constructor(
    private val bookmarkedArticleLocalDataSource: BookmarkedArticleLocalDataSource
) : BookmarkedArticleRepository {
    override suspend fun bookmark(article: Article): Long {
        return bookmarkedArticleLocalDataSource.bookmark(article.toDto())
    }

    override suspend fun unBookmark(article: Article): Int {
        return bookmarkedArticleLocalDataSource.unBookmark(article.toDto())
    }

    override suspend fun isBookMarked(article: Article): Boolean {
        return bookmarkedArticleLocalDataSource.isBookmarked(article.toDto())
    }

    override suspend fun getArticles(): Articles {
        return bookmarkedArticleLocalDataSource.getArticlesDto().toDomain()
    }
}