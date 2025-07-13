package com.devyd.articlelist.models

sealed class ArticleResult {
    object Idle : ArticleResult()
    data class Loading(val isSwipeLoading: Boolean) : ArticleResult()
    data class Success(val articlesUiState: ArticlesUiState) : ArticleResult()
    data class Failure(val error: String) : ArticleResult()
}