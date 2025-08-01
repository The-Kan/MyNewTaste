package com.devyd.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.ComposeArticleResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshArticleList(
    composeArticleResult: ComposeArticleResult,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    articleContent: @Composable (ArticleUiState) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            onRefresh()
        },
    ) {
        if (composeArticleResult is ComposeArticleResult.Success) {
            LazyColumn {
                items(
                    items = composeArticleResult.articlesUiState.articleUiState,
                ) { articleUiState ->
                    articleContent(articleUiState)
                }
            }
        }
    }
}