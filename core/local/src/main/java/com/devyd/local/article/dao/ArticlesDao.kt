package com.devyd.local.article.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.devyd.local.article.models.ArticleEntity
import com.devyd.local.article.models.ArticlesEntity
import com.devyd.local.article.models.ArticlesWithArticleEntities

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(meta: ArticlesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(articles: List<ArticleEntity>)

    @Transaction
    @Query("SELECT * FROM articles WHERE category = :category")
    suspend fun getArticlesWithArticles(category: String): ArticlesWithArticleEntities?

    @Query("DELETE FROM articles WHERE category = :category")
    suspend fun deleteMeta(category: String)

    @Query("DELETE FROM article WHERE articlesCategory = :category")
    suspend fun deleteArticlesByCategory(category: String)

    @Transaction
    suspend fun clearCategory(category: String) {
        deleteArticlesByCategory(category)
        deleteMeta(category)
    }
}