package com.devyd.data.models

import com.devyd.domain.models.Article
import com.devyd.domain.models.News
import com.devyd.domain.models.Source

data class NewsDto(
    val status: String,
    val totalResults: Int,
    val articleEntities: List<ArticleDto>
)

data class ArticleDto(
    val sourceDto: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class SourceDto(
    val id: String?,
    val name: String
)


fun NewsDto.toDomain(): News = News(
    status       = status,
    totalResults = totalResults,
    articles     = articleEntities.map { it.toDomain() }
)

fun ArticleDto.toDomain(): Article = Article(
    source       = sourceDto.toDomain(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun SourceDto.toDomain(): Source = Source(
    id   = id,
    name = name
)