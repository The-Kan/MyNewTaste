package com.devyd.bookmarkarticles.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.ui.models.ArticleResult
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.usecase.article.GetBookmarkArticleUseCase
import com.devyd.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkArticleListViewModel @Inject constructor(private val getBookmarkArticleUseCase: GetBookmarkArticleUseCase) :
    ViewModel() {
    private val _articles = MutableStateFlow<ArticleResult>(ArticleResult.Idle)
    val article = _articles.asStateFlow()

    init {
        refreshArticle(false)
    }

    fun refreshArticle(isSwipeRefresh: Boolean) {
        viewModelScope.launch {
            _articles.update { ArticleResult.Loading(isSwipeRefresh) }

            val result = runCatching { getBookmarkArticleUseCase() }
                .fold(
                    onSuccess = { news ->
                        val articlesUiState = news.toUiState()
                        ArticleResult.Success(articlesUiState)
                    },
                    onFailure = { err ->
                        LogUtil.e(logTag(), "getBookmarkArticleUseCase err : ${err.message}")
                        ArticleResult.Failure(err.message ?: "unknown error")
                    })

            _articles.update { result }
        }
    }
}