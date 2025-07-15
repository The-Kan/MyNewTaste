package com.devyd.data.repositoryimpl


import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.models.toDomain
import com.devyd.domain.models.Articles
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles(category: String): Articles {
        return articleRemoteDataSource.fetchArticlesDto(category).toDomain()
    }
}