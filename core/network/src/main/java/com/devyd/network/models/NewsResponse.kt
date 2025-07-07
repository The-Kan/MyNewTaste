package com.devyd.network.models

import com.devyd.data.models.ArticleEntity
import com.devyd.data.models.NewsEntity
import com.devyd.data.models.SourceEntity

data class NewsResponse(
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

fun NewsResponse.toEntity(): NewsEntity = NewsEntity(
    status         = status,
    totalResults   = totalResults,
    articleEntities = articles.map { it.toEntity() }
)

fun ArticleResponse.toEntity(): ArticleEntity = ArticleEntity(
    sourceEntity = source.toEntity(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun SourceResponse.toEntity(): SourceEntity = SourceEntity(
    id   = id,
    name = name
)