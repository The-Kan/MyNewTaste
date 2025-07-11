package com.devyd.data.datasource

import com.devyd.data.models.NewsDto

interface ArticleRemoteDataSource  {
    suspend fun fetchNewsEntity(): NewsDto
}