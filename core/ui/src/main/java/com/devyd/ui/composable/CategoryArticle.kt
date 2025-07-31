package com.devyd.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devyd.ui.R
import com.devyd.ui.extension.getCategorySystemName
import com.devyd.ui.models.ArticleUiState

@Composable
fun CategoryArticle(
    modifier: Modifier = Modifier,
    articleUiState: ArticleUiState,
    onArticleClick: (ArticleUiState) -> Unit,
) {
    val context = LocalContext.current

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

            val imageRequest = ImageRequest.Builder(context)
                .data(articleUiState.urlToImage)
                .crossfade(true)
                .build()

            CustomAsyncImage(imageRequest)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = context.getCategorySystemName(articleUiState.category!!),
                fontSize = 11.sp,
                color = colorResource(id = R.color.categoryColor),
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(
                    shadow = Shadow(
                        color = colorResource(id = R.color.shadowCategoryColor),
                        offset = Offset(0f, 2f),
                        blurRadius = 0.5f
                    )
                )
            )
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