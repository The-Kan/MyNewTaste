package com.devyd.network.models

import com.devyd.data.models.ArticleDto
import com.devyd.data.models.ArticlesDto
import com.devyd.data.models.SourceDto

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleResponse>
)

data class ArticleResponse(
    val source: SourceResponse,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class SourceResponse(
    val id: String?,
    val name: String
)

fun ArticlesResponse.toEntity(): ArticlesDto = ArticlesDto(
    status         = status,
    totalResults   = totalResults,
    articleEntities = articles.map { it.toEntity() }
)

fun ArticleResponse.toEntity(): ArticleDto = ArticleDto(
    sourceDto = source.toEntity(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun SourceResponse.toEntity(): SourceDto = SourceDto(
    id   = id,
    name = name
)