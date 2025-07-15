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

fun ArticlesResponse.toDto(): ArticlesDto = ArticlesDto(
    status         = status,
    totalResults   = totalResults,
    articleEntities = articles.map { it.toDto() }
)

fun ArticleResponse.toDto(): ArticleDto = ArticleDto(
    sourceDto = source.toDto(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun SourceResponse.toDto(): SourceDto = SourceDto(
    id   = id,
    name = name
)