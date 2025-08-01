package com.devyd.allcategoryarticles


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devyd.allcategoryarticles.vm.ComposeAllArticleListViewModel
import com.devyd.ui.composable.AirplaneProgressLottie
import com.devyd.ui.composable.Article
import com.devyd.ui.composable.PullToRefreshArticleList
import com.devyd.ui.models.ArticleUiState
import com.devyd.ui.models.ComposeArticleResult


@Composable
fun AllArticleListScreen(
    onArticleClick: (ArticleUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ComposeAllArticleListViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val articleState by viewModel.article.collectAsStateWithLifecycle()

        when (val articleResult = articleState) {

            is ComposeArticleResult.Failure -> {
                Button(
                    onClick = {
                        viewModel.refreshArticle(false)
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
                        viewModel.refreshArticle(true)
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

