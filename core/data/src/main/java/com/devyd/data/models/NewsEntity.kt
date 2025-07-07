package com.devyd.data.models

import com.devyd.domain.models.Article
import com.devyd.domain.models.News
import com.devyd.domain.models.Source

data class NewsEntity(
    val status: String,
    val totalResults: Int,
    val articleEntities: List<ArticleEntity>
)

data class ArticleEntity(
    val sourceEntity: SourceEntity,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class SourceEntity(
    val id: String?,
    val name: String
)


fun NewsEntity.toDomain(): News = News(
    status       = status,
    totalResults = totalResults,
    articles     = articleEntities.map { it.toDomain() }
)

fun ArticleEntity.toDomain(): Article = Article(
    source       = sourceEntity.toDomain(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun SourceEntity.toDomain(): Source = Source(
    id   = id,
    name = name
)