package com.devyd.common.extension

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * In compose, 'collectAsState()' doesn't receive State emits that arrive within '5ms'
 * so it waits for a delay of 10ms.
 * Todo - We need a better alternative.
 */
suspend fun <T> MutableStateFlow<T>.updateAndDelay(delayMillis: Long = 10L, function: (T) -> T) {
    this.update(function)
    delay(delayMillis)
}