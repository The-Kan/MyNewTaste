package com.devyd.tastearticles.models

import com.devyd.ui.models.ArticlesUiState

sealed class ComposeTasteArticleResult {
    data object Idle : ComposeTasteArticleResult()
    data object NeedToCategorySetting : ComposeTasteArticleResult()
    data object Loading : ComposeTasteArticleResult()
    data object Refreshing: ComposeTasteArticleResult()
    data class Success(val articlesUiState: ArticlesUiState) : ComposeTasteArticleResult()
    data class Failure(val error: String) : ComposeTasteArticleResult()
}