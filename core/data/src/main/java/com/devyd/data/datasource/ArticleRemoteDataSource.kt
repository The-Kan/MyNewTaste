package com.devyd.data.datasource

import com.devyd.data.models.NewsEntity

interface ArticleRemoteDataSource  {
    suspend fun fetchNewsEntity(): NewsEntity
}