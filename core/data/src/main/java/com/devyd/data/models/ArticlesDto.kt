package com.devyd.data.models

import com.devyd.domain.models.Article
import com.devyd.domain.models.Articles
import com.devyd.domain.models.Source

data class ArticlesDto(
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


fun ArticlesDto.toDomain(): Articles = Articles(
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


fun Article.toDto(): ArticleDto = ArticleDto(
    sourceDto       = source.toDto(),
    author       = author,
    title        = title,
    description  = description,
    url          = url,
    urlToImage   = urlToImage,
    publishedAt  = publishedAt,
    content      = content
)

fun Source.toDto(): SourceDto = SourceDto(
    id   = id,
    name = name
)