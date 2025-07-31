package com.devyd.categoryarticles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devyd.categoryarticles.vm.ComposeCategoryArticleListViewModel
import com.devyd.ui.composable.AirplaneProgressLottie
import com.devyd.ui.composable.Article
import com.devyd.ui.composable.PullToRefreshArticleList
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.ComposeArticleResult


@Composable
fun CategoryArticleListScreen(
    category: String, onArticleClick: (ArticleUiState) -> Unit
) {
    val viewModel = hiltViewModel<ComposeCategoryArticleListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.initParams(category)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val articleState by viewModel.article.collectAsState()


        when (val articleResult = articleState) {
            is ComposeArticleResult.Failure -> {
                Button(
                    onClick = {
                        viewModel.refreshArticle(false, category)
                    }
                ) {
                    Text("Fail!! retry")
                }
            }

            ComposeArticleResult.Idle -> {

            }

            is ComposeArticleResult.Loading -> {
                AirplaneProgressLottie()
            }

            is ComposeArticleResult.Success, ComposeArticleResult.Refreshing -> {
                PullToRefreshArticleList(
                    composeArticleResult = articleResult,
                    isRefreshing = articleResult == ComposeArticleResult.Refreshing,
                    onRefresh = {
                        viewModel.refreshArticle(true, category)
                    },
                    articleContent = {
                        Article(
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