package com.devyd.categoryarticles.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.ui.models.ArticleResult
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.ui.models.toUiState
import com.devyd.domain.usecase.article.GetArticleUseCase
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
                    onSuccess = { news ->
                        val articlesUiState = news.toUiState()
                        val newArticlesUiState =
                            articlesUiState.copy(articleUiState = articlesUiState.articleUiState.map {
                                it.copy(category = category)
                            })
                        ArticleResult.Success(newArticlesUiState)
                    },
                    onFailure = { err ->
                        LogUtil.e(logTag(), "getArticleUseCase err : ${err.message}")
                        ArticleResult.Failure(err.message ?: "unknown error")
                    })

            _articles.update { result }
        }
    }
}