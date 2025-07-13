package com.devyd.mynewstaste.mapper

import com.devyd.articlelist.models.ArticleUiState
import com.devyd.articlelist.models.SourceUiState


fun ArticleUiState.toUiState(): com.devyd.articledetail.models.ArticleUiState = com.devyd.articledetail.models.ArticleUiState(
    sourceUiState = sourceUiState.toUiState(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun SourceUiState.toUiState(): com.devyd.articledetail.models.SourceUiState = com.devyd.articledetail.models.SourceUiState(
    id = id,
    name = name
)