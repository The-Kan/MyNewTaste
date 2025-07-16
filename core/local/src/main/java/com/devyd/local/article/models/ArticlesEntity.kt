package com.devyd.local.article.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.devyd.data.models.ArticleDto
import com.devyd.data.models.ArticlesDto
import com.devyd.data.models.SourceDto

@Entity(tableName = "articles")
data class ArticlesEntity(
    @PrimaryKey val category: String,
    val status: String,
    val totalResults: Int,
    val savedAt: Long
)

@Entity(
    tableName = "article",
    foreignKeys = [
        ForeignKey(
            entity = ArticlesEntity::class,
            parentColumns = ["category"],
            childColumns = ["articlesCategory"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("articlesCategory")]
)

data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articlesCategory: String,

    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,

    @Embedded(prefix = "source_")
    val sourceDto: SourceEmbedded
)

data class SourceEmbedded(
    val id: String?,
    val name: String
)

data class ArticlesWithArticleEntities(
    @Embedded val articlesEntity: ArticlesEntity,

    @Relation(
        parentColumn = "category",
        entityColumn = "articlesCategory"
    )
    val articleList: List<ArticleEntity>
)

fun ArticlesDto.toEntities(category: String): Pair<ArticlesEntity, List<ArticleEntity>> {
    val currentUtcTime = System.currentTimeMillis()

    val meta = ArticlesEntity(
        category = category,
        status = status,
        totalResults = totalResults,
        savedAt = currentUtcTime
    )

    val articleEntities = articleEntities.map {
        ArticleEntity(
            articlesCategory = category,
            sourceDto = SourceEmbedded(it.sourceDto.id, it.sourceDto.name),
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }

    return meta to articleEntities
}

fun ArticlesWithArticleEntities.toDto(): ArticlesDto {
    return ArticlesDto(
        status = articlesEntity.status,
        totalResults = articlesEntity.totalResults,
        articleEntities = articleList.map { it.toDto() }
    )
}

fun ArticleEntity.toDto(): ArticleDto {
    return ArticleDto(
        sourceDto = SourceDto(
            id = sourceDto.id,
            name = sourceDto.name
        ),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}