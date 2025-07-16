package com.devyd.local.article.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devyd.local.article.dao.ArticlesDao
import com.devyd.local.article.models.ArticleEntity
import com.devyd.local.article.models.ArticlesEntity

@Database(
    entities = [ArticlesEntity::class, ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}