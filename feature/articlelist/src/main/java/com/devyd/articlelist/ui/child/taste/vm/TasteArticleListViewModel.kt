package com.devyd.articlelist.ui.child.taste.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.articlelist.models.ArticleResult
import com.devyd.articlelist.models.ArticleUiState
import com.devyd.articlelist.models.ArticlesUiState
import com.devyd.articlelist.models.toUiState
import com.devyd.common.CategoryStrings
import com.devyd.common.models.CategoryWeightResult
import com.devyd.domain.usecase.GetArticleUseCase
import com.devyd.domain.usecase.categoryweight.GetCategoryWeightsUseCase
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
    private val _articles = MutableStateFlow<ArticleResult>(ArticleResult.Idle)
    val article = _articles.asStateFlow()

    init {
        refreshArticle(false)
    }

    fun refreshArticle(isSwipeRefresh: Boolean) {
        viewModelScope.launch {
            _articles.update { ArticleResult.Loading(isSwipeRefresh) }

            // DB에서 비율 가져오기.
            val getCategoryWeightsUseCaseResult = kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->
                    CategoryWeightResult.Success(list)
                },
                onFailure = { error ->
                    CategoryWeightResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            if (getCategoryWeightsUseCaseResult is CategoryWeightResult.Failure) {
                _articles.update { ArticleResult.Failure(getCategoryWeightsUseCaseResult.error) }
            } else if (getCategoryWeightsUseCaseResult is CategoryWeightResult.Success) {
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
                                ArticleResult.Success(newArticlesUiState)
                            },
                            onFailure = { err ->
                                ArticleResult.Failure(
                                    err.message ?: "unknown error"
                                )
                            })

                    val queue = LinkedList<ArticleUiState>()

                    if (getArticleUseCaseResult is ArticleResult.Success) {
                        queue.addAll(getArticleUseCaseResult.articlesUiState.articleUiState)
                        map[category] = queue
                    }

                }

                val categoryWeightList = getCategoryWeightsUseCaseResult.categoryWeightList
                val articleUiStateList = mutableListOf<ArticleUiState>()
                val min = categoryWeightList.minOf { it.weight }
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

                val result = ArticleResult.Success(
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