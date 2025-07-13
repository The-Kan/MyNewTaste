package com.devyd.common.models

import android.os.Parcelable
import com.devyd.domain.models.Article
import com.devyd.domain.models.Articles
import com.devyd.domain.models.Source
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArticlesUiState(
    val status: String,
    val totalResults: Int,
    val articleUiState: List<ArticleUiState>
) : Parcelable

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


fun Articles.toUiSate(): ArticlesUiState = ArticlesUiState(
    status = status,
    totalResults = totalResults,
    articleUiState = articles.map { it.toUiSate() }.filter { !it.urlToImage.isNullOrEmpty() }
)

fun Article.toUiSate(): ArticleUiState = ArticleUiState(
    sourceUiState = source.toUiSate(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun Source.toUiSate(): SourceUiState = SourceUiState(
    id = id,
    name = name
)