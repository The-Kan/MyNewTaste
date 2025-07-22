package com.devyd.local.bookmarkedarticle.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.devyd.data.models.ArticleDto
import com.devyd.data.models.SourceDto

@Entity(
    tableName = "bookmarked_article",
    indices = [Index(value = ["title", "publishedAt"], unique = true)]
)
data class BookMarkedArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,

    @Embedded(prefix = "source_")
    val sourceDto: BookMarkedSource
)

data class BookMarkedSource(
    val id: String?,
    val name: String
)

fun ArticleDto.toEntity(): BookMarkedArticleEntity = BookMarkedArticleEntity(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    sourceDto = sourceDto.toEntity()
)

fun SourceDto.toEntity(): BookMarkedSource = BookMarkedSource(
    id = id,
    name = name
)


