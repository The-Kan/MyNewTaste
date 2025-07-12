package com.devyd.articlelist.models

import com.devyd.domain.models.Articles

sealed class ArticleResult {
    object Idle : ArticleResult()
    object Loading : ArticleResult()
    data class Success(val articles: Articles) : ArticleResult()
    data class Failure(val error: String) : ArticleResult()
}