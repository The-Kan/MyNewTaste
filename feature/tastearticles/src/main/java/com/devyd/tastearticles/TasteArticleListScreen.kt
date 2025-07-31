package com.devyd.tastearticles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devyd.tastearticles.models.ComposeTasteArticleResult
import com.devyd.tastearticles.vm.ComposeTasteArticleListViewModel
import com.devyd.ui.composable.AirplaneProgressLottie
import com.devyd.ui.composable.CategoryArticle
import com.devyd.ui.models.ArticleUiState

@Composable
fun TasteArticleListScreen(
    onArticleClick: (ArticleUiState) -> Unit,
    onSetCategoryClick: () -> Unit
) {
    val viewModel = hiltViewModel<ComposeTasteArticleListViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val articleState by viewModel.article.collectAsStateWithLifecycle()

        when (val articleResult = articleState) {

            is ComposeTasteArticleResult.Failure -> {
                Button(
                    onClick = {
                        viewModel.refreshArticle(false)
                    }
                ) {
                    Text("Fail!! retry")
                }
            }

            ComposeTasteArticleResult.Idle -> {}

            is ComposeTasteArticleResult.Loading -> {
                AirplaneProgressLottie()
            }

            ComposeTasteArticleResult.NeedToCategorySetting -> {
                CategorySettingGuide(onSetCategoryClick)
            }

            is ComposeTasteArticleResult.Success, ComposeTasteArticleResult.Refreshing -> {
                PullToRefreshTasteArticleList(
                    composeTasteArticleResult = articleResult,
                    isRefreshing = articleResult == ComposeTasteArticleResult.Refreshing,
                    onRefresh = {
                        viewModel.refreshArticle(true)
                    },
                    articleContent = {
                        CategoryArticle(
                            modifier = Modifier
                                .fillMaxWidth(),
                            articleUiState = it,
                            onArticleClick = onArticleClick
                        )
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshTasteArticleList(
    modifier: Modifier = Modifier,
    composeTasteArticleResult: ComposeTasteArticleResult,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
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
        if (composeTasteArticleResult is ComposeTasteArticleResult.Success) {
            LazyColumn {
                items(
                    items = composeTasteArticleResult.articlesUiState.articleUiState,
                ) { articleUiState ->
                    articleContent(articleUiState)
                }
            }
        }
    }
}


@Composable
fun CategorySettingGuide(
    onSetCategoryClick: () -> Unit
) {

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.category_setting_guide),
            color = Color(0xFF333333),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onSetCategoryClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.category_setting))
        }
    }
}