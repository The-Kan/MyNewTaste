package com.devyd.ui.models

sealed class ComposeArticleResult {
    data object Idle : ComposeArticleResult()
    data object Loading : ComposeArticleResult()
    data object Refreshing : ComposeArticleResult()
    data class Success(val articlesUiState: ArticlesUiState) : ComposeArticleResult()
    data class Failure(val error: String) : ComposeArticleResult()
}