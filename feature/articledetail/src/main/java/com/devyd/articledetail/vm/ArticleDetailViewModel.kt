package com.devyd.articledetail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.articledetail.models.ArticleUiStateResult
import com.devyd.articledetail.models.BookmarkResult
import com.devyd.articledetail.models.UnBookMarkResult
import com.devyd.domain.usecase.article.BookmarkArticleUseCase
import com.devyd.domain.usecase.article.UnBookmarkArticleUseCase
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val unBookmarkArticleUseCase : UnBookmarkArticleUseCase
) : ViewModel() {
    private var _articleUiState = MutableStateFlow<ArticleUiStateResult>(ArticleUiStateResult.Loading)
    val articleUiState = _articleUiState.asStateFlow()

    private var _bookMarkArticleResult = MutableSharedFlow<BookmarkResult>()
    val bookMarkArticleResult = _bookMarkArticleResult.asSharedFlow()

    private var _unBookMarkArticleResult = MutableSharedFlow<UnBookMarkResult>()
    val unBookMarkArticleResult = _unBookMarkArticleResult.asSharedFlow()

    fun setArticleUiState(articleUiState: ArticleUiState?) {
        if(articleUiState == null) {
            _articleUiState.value = ArticleUiStateResult.Failure("ArticleUiState is null")
        } else {
            _articleUiState.value = ArticleUiStateResult.Success(articleUiState)
        }
    }

    fun bookMarkArticle(articleUiState: ArticleUiState) {
        viewModelScope.launch {
            val result = kotlin.runCatching { bookmarkArticleUseCase(articleUiState.toDomain()) }
                .fold(onSuccess = {idx -> BookmarkResult.Success(idx)}, onFailure = {err -> BookmarkResult.Failure(err.message ?: "unknown error")})
            _bookMarkArticleResult.emit(result)
        }
    }

    fun unBookMarkArticle(articleUiState: ArticleUiState) {
        viewModelScope.launch {
            val result = kotlin.runCatching { unBookmarkArticleUseCase(articleUiState.toDomain()) }
                .fold(onSuccess = {rowCnt -> UnBookMarkResult.Success(rowCnt)}, onFailure = {err -> UnBookMarkResult.Failure(err.message ?: "unknown error")})
            _unBookMarkArticleResult.emit(result)
        }
    }


}