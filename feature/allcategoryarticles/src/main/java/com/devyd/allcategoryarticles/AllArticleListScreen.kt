package com.devyd.allcategoryarticles


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devyd.allcategoryarticles.vm.ComposeAllArticleListViewModel
import com.devyd.ui.models.ArticleResult
import com.devyd.ui.models.ArticleUiState


@Composable
fun AllArticleListScreen(
    onArticleClick: (ArticleUiState) -> Unit
) {

    val viewModel = hiltViewModel<ComposeAllArticleListViewModel>()
    var isRefreshing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val articleState by viewModel.article.collectAsStateWithLifecycle()

        when (val articleResult = articleState) {

            is ArticleResult.Failure -> {
                isRefreshing = false
                Button(
                    onClick = {
                        viewModel.refreshArticle(false)
                    }
                ) {
                    Text("Fail!! retry")
                }
            }

            ArticleResult.Idle -> {
                isRefreshing = false
            }

            is ArticleResult.Loading -> {
                val isSwipeLoading = articleResult.isSwipeLoading
                AirplaneProgressLottie(
                    visible = !isSwipeLoading
                )
            }

            is ArticleResult.Success -> {
                RefreshArticleList(
                    articleResult.articlesUiState.articleUiState,
                    onRefresh = {
                        viewModel.refreshArticle(true)
                    },
                    onArticleClick
                )
            }
        }


    }

}

@Composable
fun AirplaneProgressLottie(
    modifier: Modifier = Modifier,
    visible: Boolean,
    autoPlay: Boolean = true,
    loop: Boolean = true
) {
    if (!visible) return

    val composition =
        rememberLottieComposition(LottieCompositionSpec.Asset("airplane_progress_lottie.json")).value

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = autoPlay,
        iterations = if (loop) LottieConstants.IterateForever else 1,
        restartOnPlay = true
    )

    if (composition != null) {
        LottieAnimation(
            composition = composition,
            progress = { progress }, // recompositon을 방지하기위해 람다로 전달
            modifier = modifier.fillMaxSize()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshArticleList(
    articleUiStateList: List<ArticleUiState>,
//    isRefreshing: Boolean,
    onRefresh: () -> Unit,
//    modifier: Modifier = Modifier
    onArticleClick: (ArticleUiState) -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }


    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

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


@Composable
fun Article(
    modifier: Modifier = Modifier,
    articleUiState: ArticleUiState,
    onArticleClick: (ArticleUiState) -> Unit,
) {

    Card(
        modifier = modifier
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        onClick = {
            onArticleClick(articleUiState)
        }
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {

            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(articleUiState.urlToImage)
                .crossfade(true)
                .build()

            CustomAsyncImage(imageRequest)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = articleUiState.title,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun CustomAsyncImage(
    imageRequest: ImageRequest,
    modifier: Modifier = Modifier,
    errorPainter: Painter = painterResource(com.devyd.ui.R.drawable.landscape_1),
) {
    val painter = rememberAsyncImagePainter(model = imageRequest)
    val state by painter.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        when (state) {
            is AsyncImagePainter.State.Error -> {
                Image(
                    modifier = modifier,
                    painter = errorPainter,
                    contentDescription = "error image",
                    contentScale = ContentScale.Inside,
                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = modifier,
                    painter = painter,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Inside,
                )
            }

            else -> {
                ShimmerRect(modifier = modifier)
            }
        }
    }
}