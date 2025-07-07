package com.devyd.data.repositoryimpl

import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.domain.models.Article
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        articleRemoteDataSource.fetchArticles()
        // 데이터 변환 ArticleDTO -> Article
        return emptyList()
    }
}