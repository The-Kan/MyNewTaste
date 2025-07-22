package com.devyd.articlelist.ui.child.allcategory.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.articlelist.models.ArticleResult
import com.devyd.common.CategoryStrings
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.usecase.article.GetArticleUseCase
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.ArticlesUiState
import com.devyd.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllArticleListViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) :
    ViewModel() {
    private val _articles = MutableStateFlow<ArticleResult>(ArticleResult.Idle)
    val article = _articles.asStateFlow()

    init {
        refreshArticle(false)
    }

    fun refreshArticle(isSwipeRefresh: Boolean) {
        viewModelScope.launch {
            _articles.update { ArticleResult.Loading(isSwipeRefresh) }


            val articleUiStateList = mutableListOf<ArticleUiState>()

            for (category in CategoryStrings.validValues) {
                val getArticleUseCaseResult = runCatching { getArticleUseCase(category) }
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
                            ArticleResult.Failure(
                                err.message ?: "unknown error"
                            )
                        })

                if (getArticleUseCaseResult is ArticleResult.Success) {
                    articleUiStateList.addAll(getArticleUseCaseResult.articlesUiState.articleUiState)
                }
            }


            val result = ArticleResult.Success(
                ArticlesUiState(
                    status = "status",
                    totalResults = 1,
                    articleUiState = articleUiStateList.shuffled()
                )
            )
            _articles.update { result }
        }
    }
}
