package com.devyd.bookmarkarticles.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.extension.updateAndDelay
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.usecase.article.GetBookmarkArticleUseCase
import com.devyd.ui.models.ComposeArticleResult
import com.devyd.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ComposeBookmarkArticleListViewModel @Inject constructor(private val getBookmarkArticleUseCase: GetBookmarkArticleUseCase) :
    ViewModel() {
    private val _articles = MutableStateFlow<ComposeArticleResult>(ComposeArticleResult.Idle)
    val article = _articles.asStateFlow()

    init {
        refreshArticle(false)
    }

    fun refreshArticle(isSwipeRefresh: Boolean) {
        viewModelScope.launch {

            if (isSwipeRefresh) {
                _articles.updateAndDelay { ComposeArticleResult.Refreshing }
            } else {
                _articles.update { ComposeArticleResult.Loading }
            }


            val result = runCatching { getBookmarkArticleUseCase() }
                .fold(
                    onSuccess = { news ->
                        val articlesUiState = news.toUiState()
                        ComposeArticleResult.Success(articlesUiState)
                    },
                    onFailure = { err ->
                        LogUtil.e(logTag(), "getBookmarkArticleUseCase err : ${err.message}")
                        ComposeArticleResult.Failure(err.message ?: "unknown error")
                    })

            _articles.update { result }
        }
    }
}