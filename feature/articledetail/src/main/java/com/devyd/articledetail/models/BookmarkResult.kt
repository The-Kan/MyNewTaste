package com.devyd.articledetail.models

sealed class BookmarkResult {
    data class Success(val idx: Long) : BookmarkResult()
    data class Failure(val err: String) : BookmarkResult()
}

sealed class UnBookMarkResult {
    data class Success(val num: Int) : UnBookMarkResult()
    data class Failure(val err: String) : UnBookMarkResult()
}