package com.devyd.articledetail.models

import com.devyd.ui.models.ArticleUiState

sealed class ArticleUiStateResult {
    object Loading : ArticleUiStateResult()
    data class Success(val articleUiState: ArticleUiState) : ArticleUiStateResult()
    data class Failure(val err: String) : ArticleUiStateResult()
}