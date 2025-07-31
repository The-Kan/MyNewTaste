package com.devyd.categoryarticles.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.extension.updateAndDelay
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.usecase.article.GetArticleUseCase
import com.devyd.ui.models.ComposeArticleResult
import com.devyd.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ComposeCategoryArticleListViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) :
    ViewModel() {
    private val _articles = MutableStateFlow<ComposeArticleResult>(ComposeArticleResult.Idle)
    val article = _articles.asStateFlow()

    private var isInit = AtomicBoolean(false)

    fun initParams(category: String) {
        if (isInit.compareAndSet(false, true)) {
            refreshArticle(false, category)
        }
    }

    fun refreshArticle(isSwipeRefresh: Boolean, category: String) {
        viewModelScope.launch {


            if (isSwipeRefresh) {
                _articles.updateAndDelay {
                    ComposeArticleResult.Refreshing
                }
            } else {
                _articles.update { ComposeArticleResult.Loading }
            }


            val result = runCatching { getArticleUseCase(category) }
                .fold(
                    onSuccess = { news ->
                        val articlesUiState = news.toUiState()
                        val newArticlesUiState =
                            articlesUiState.copy(articleUiState = articlesUiState.articleUiState.map {
                                it.copy(category = category)
                            })
                        ComposeArticleResult.Success(newArticlesUiState)
                    },
                    onFailure = { err ->
                        LogUtil.e(logTag(), "getArticleUseCase err : ${err.message}")
                        ComposeArticleResult.Failure(err.message ?: "unknown error")
                    })

            _articles.update {
                result
            }
        }
    }
}
