package com.devyd.articlelist.ui.child.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.domain.models.Article
import com.devyd.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val article = _articles.asStateFlow()

    fun refreshArticle() {
        viewModelScope.launch {
            LogUtil.i(logTag(),"article launch")
            _articles.update { getArticleUseCase() }
        }
    }
}