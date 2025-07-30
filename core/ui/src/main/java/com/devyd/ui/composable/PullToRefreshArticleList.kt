package com.devyd.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devyd.ui.models.ArticleUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshArticleList(
    modifier: Modifier = Modifier,
    articleUiStateList: List<ArticleUiState>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit,
    onArticleClick: (ArticleUiState) -> Unit
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn {

            items(
                items = articleUiStateList,
            ) { articleUiState ->
                Article(
                    modifier = Modifier
                        .fillMaxWidth(),
                    articleUiState = articleUiState,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}