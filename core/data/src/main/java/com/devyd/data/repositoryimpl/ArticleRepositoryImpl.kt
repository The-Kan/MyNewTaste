package com.devyd.data.repositoryimpl


import com.devyd.data.datasource.ArticleLocalDataSource
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.models.toDomain
import com.devyd.domain.models.Articles
import com.devyd.domain.repository.ArticleRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/**
 * This class should always be operated as a 'Singleton'.
 * ArticleRepository caches the results of web requests to the DB.
 * This is to reduce redundant web requests and to refer to cached DB values.
 *
 * This effect can reduce server traffic and save costs.
 * Also, the loading speed is faster because the DB is searched faster than the web request.
 */

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource
) : ArticleRepository {
    private val mutexMap = ConcurrentHashMap<String, Mutex>()

    override suspend fun getArticles(category: String): Articles {
        val mutex = mutexMap.getOrPut(category) { Mutex() }

        return mutex.withLock {
            var articlesDto = articleLocalDataSource.getArticlesDto(category)
            if (articlesDto != null) return articlesDto.toDomain()

            articlesDto = articleRemoteDataSource.fetchArticlesDto(category)
            articleLocalDataSource.add(category, articlesDto)

            articlesDto.toDomain()
        }
    }
}