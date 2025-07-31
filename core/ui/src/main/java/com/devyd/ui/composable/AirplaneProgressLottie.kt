package com.devyd.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun AirplaneProgressLottie(
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    loop: Boolean = true
) {
    val composition =
        rememberLottieComposition(LottieCompositionSpec.Asset("airplane_progress_lottie.json")).value

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = autoPlay,
        iterations = if (loop) LottieConstants.IterateForever else 1,
        restartOnPlay = true
    )

    LottieAnimation(
        composition = composition,
        progress = { progress }, // recompositon을 방지하기위해 람다로 전달
        modifier = modifier.fillMaxSize()
    )
}