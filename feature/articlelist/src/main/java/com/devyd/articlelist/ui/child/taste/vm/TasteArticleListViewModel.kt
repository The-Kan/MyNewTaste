package com.devyd.articlelist.ui.child.taste.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.ArticlesUiState
import com.devyd.articlelist.models.TasteArticleResult
import com.devyd.ui.models.toUiState
import com.devyd.common.CategoryStrings
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.usecase.article.GetArticleUseCase
import com.devyd.domain.usecase.categoryweight.GetCategoryWeightsUseCase
import com.devyd.ui.models.CategoryWeightUiStateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedList
import javax.inject.Inject

@HiltViewModel
class TasteArticleListViewModel @Inject constructor(
    private val getCategoryWeightsUseCase: GetCategoryWeightsUseCase,
    private val getArticleUseCase: GetArticleUseCase
) :
    ViewModel() {
    private val _articles = MutableStateFlow<TasteArticleResult>(TasteArticleResult.Idle)
    val article = _articles.asStateFlow()

    init {
        refreshArticle(false)
    }

    fun refreshArticle(isSwipeRefresh: Boolean) {
        viewModelScope.launch {
            _articles.update { TasteArticleResult.Loading(isSwipeRefresh) }

            // DB에서 비율 가져오기.
            val getCategoryWeightsUseCaseResult = kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->

                    CategoryWeightUiStateResult.Success(list.map { it.toUiState() })
                },
                onFailure = { error ->
                    CategoryWeightUiStateResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            if (getCategoryWeightsUseCaseResult is CategoryWeightUiStateResult.Failure) {
                _articles.update { TasteArticleResult.Failure(getCategoryWeightsUseCaseResult.error) }
            } else if (getCategoryWeightsUseCaseResult is CategoryWeightUiStateResult.Success) {
                val map = mutableMapOf<String, LinkedList<ArticleUiState>>()

                for (category in CategoryStrings.validValues) {
                    val getArticleUseCaseResult = runCatching { getArticleUseCase(category) }
                        .fold(
                            onSuccess = { news ->
                                val articlesUiState = news.toUiState()
                                val newArticlesUiState =
                                    articlesUiState.copy(articleUiState = articlesUiState.articleUiState.map {
                                        it.copy(category = category)
                                    })
                                TasteArticleResult.Success(newArticlesUiState)
                            },
                            onFailure = { err ->
                                LogUtil.e(logTag(),"getArticleUseCase err : ${err.message}")
                                TasteArticleResult.Failure(
                                    err.message ?: "unknown error"
                                )
                            })

                    val queue = LinkedList<ArticleUiState>()

                    if (getArticleUseCaseResult is TasteArticleResult.Success) {
                        queue.addAll(getArticleUseCaseResult.articlesUiState.articleUiState)
                        map[category] = queue
                    }

                }

                val categoryWeightList = getCategoryWeightsUseCaseResult.categoryWeightUiStateList

                if (categoryWeightList.isEmpty()) {
                    // 이 지점에서 CategoryWeight를 설정할 수 있도록 해야함.
                    _articles.update { TasteArticleResult.NeedToCategorySetting }
                } else {
                    val articleUiStateList = mutableListOf<ArticleUiState>()
                    val min = categoryWeightList.minOf { it.weight }.coerceAtLeast(Constants.CATEGORY_SLIDER_MIN.toInt())
                    val max = map.values.maxOf { it.size }
                    val maxRepeatCount = max / min + 1
                    outer@ for (i in 0..maxRepeatCount) {
                        for (j in 0..categoryWeightList.lastIndex) {
                            val category = categoryWeightList[j].category
                            val weight = categoryWeightList[j].weight
                            val articleResultQueue = map[category]!!
                            if (articleResultQueue.isEmpty()) break@outer

                            repeat(weight) {
                                if (articleResultQueue.isNotEmpty()) {

                                    articleUiStateList.add(articleResultQueue.pop())
                                }
                            }
                        }
                    }

                    val result = TasteArticleResult.Success(
                        ArticlesUiState(
                            status = "status",
                            totalResults = 1,
                            articleUiState = articleUiStateList
                        )
                    )
                    _articles.update { result }
                }


            }
        }
    }
}