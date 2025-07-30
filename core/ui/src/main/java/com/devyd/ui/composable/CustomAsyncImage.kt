package com.devyd.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.devyd.ui.animation.ShimmerRect

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
                    modifier = modifier
                        .fillMaxSize(),
                    painter = errorPainter,
                    contentDescription = "error image",
                    contentScale = ContentScale.Crop,
                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = modifier
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop,
                )
            }

            else -> {
                ShimmerRect(modifier = modifier)
            }
        }
    }
}