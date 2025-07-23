package com.devyd.local.article.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devyd.local.bookmarkedarticle.models.BookMarkedArticleEntity

@Dao
interface BookMarkedArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(bookMarkedArticleEntity: BookMarkedArticleEntity): Long

    @Query(
        """
        DELETE FROM bookmarked_article 
        WHERE title = :title AND publishedAt = :publishedAt
    """
    )
    suspend fun deleteByTitleAndPublishedAt(title: String, publishedAt: String): Int

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM bookmarked_article
            WHERE title = :title AND publishedAt = :publishedAt
        )
    """
    )
    suspend fun isBookmarked(title: String, publishedAt: String): Boolean

    @Query("SELECT * FROM bookmarked_article")
    suspend fun getAll(): List<BookMarkedArticleEntity>
}