package com.devyd.articlelist.ui.child.category.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.articlelist.models.ArticleResult
import com.devyd.articlelist.models.toUiState
import com.devyd.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryArticleListViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) :
    ViewModel() {
    private val _articles = MutableStateFlow<ArticleResult>(ArticleResult.Idle)
    val article = _articles.asStateFlow()

    fun initParams(category: String) {
        refreshArticle(false, category)
    }

    fun refreshArticle(isSwipeRefresh: Boolean, category: String) {
        viewModelScope.launch {
            _articles.update { ArticleResult.Loading(isSwipeRefresh) }

            val result = runCatching { getArticleUseCase(category) }
                .fold(
                    onSuccess = { news -> ArticleResult.Success(news.toUiState()) },
                    onFailure = { err -> ArticleResult.Failure(err.message ?: "unknown error") })

            _articles.update { result }
        }
    }
}