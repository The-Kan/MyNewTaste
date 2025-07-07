package com.devyd.domain.repository

import com.devyd.domain.models.News

interface ArticleRepository {
    suspend fun getNews(): News
}