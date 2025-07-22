package com.devyd.ui.models

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
    val content: String?,
    val category: String? = null
) : Parcelable

@Parcelize
data class SourceUiState(
    val id: String?,
    val name: String
) : Parcelable


fun Articles.toUiState(): ArticlesUiState = ArticlesUiState(
    status = status,
    totalResults = totalResults,
    articleUiState = articles.map { it.toUiState() }.filter { !it.urlToImage.isNullOrEmpty() }
)

fun Article.toUiState(): ArticleUiState = ArticleUiState(
    sourceUiState = source.toUiState(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun Source.toUiState(): SourceUiState = SourceUiState(
    id = id,
    name = name
)

fun ArticleUiState.toDomain(): Article = Article(
    source = sourceUiState.toDomain(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun SourceUiState.toDomain(): Source = Source(
    id = id,
    name = name
)