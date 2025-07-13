package com.devyd.articledetail.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArticleUiState(
    val sourceUiState: SourceUiState,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable

@Parcelize
data class SourceUiState(
    val id: String?,
    val name: String
) : Parcelable