package com.devyd.data.repositoryimpl

import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.models.toDomain
import com.devyd.domain.models.Articles
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    override suspend fun getNews(category: String): Articles {
        LogUtil.i(logTag(),"Deok thread = ${Thread.currentThread()}")

        return articleRemoteDataSource.fetchNewsEntity(category).toDomain()
    }
}