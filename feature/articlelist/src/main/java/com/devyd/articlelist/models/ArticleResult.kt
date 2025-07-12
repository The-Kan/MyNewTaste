package com.devyd.articlelist.models

import com.devyd.common.models.ArticlesUiState

sealed class ArticleResult {
    object Idle : ArticleResult()
    object Loading : ArticleResult()
    data class Success(val articlesUiState: ArticlesUiState) : ArticleResult()
    data class Failure(val error: String) : ArticleResult()
}