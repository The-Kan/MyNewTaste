package com.devyd.tastearticles.models

import com.devyd.ui.models.ArticlesUiState

sealed class TasteArticleResult {
    object Idle : TasteArticleResult()
    object NeedToCategorySetting : TasteArticleResult()
    data class Loading(val isSwipeLoading: Boolean) : TasteArticleResult()
    data class Success(val articlesUiState: ArticlesUiState) : TasteArticleResult()
    data class Failure(val error: String) : TasteArticleResult()
}