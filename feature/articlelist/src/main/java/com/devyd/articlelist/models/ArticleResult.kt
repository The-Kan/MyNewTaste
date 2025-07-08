package com.devyd.articlelist.models

import com.devyd.domain.models.News

sealed class ArticleResult {
    object Idle : ArticleResult()
    object Loading : ArticleResult()
    data class Success(val news: News) : ArticleResult()
    data class Failure(val error: String) : ArticleResult()
}