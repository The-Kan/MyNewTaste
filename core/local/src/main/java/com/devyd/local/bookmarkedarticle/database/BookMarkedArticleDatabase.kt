package com.devyd.local.bookmarkedarticle.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devyd.local.article.dao.BookMarkedArticleDao
import com.devyd.local.bookmarkedarticle.models.BookMarkedArticleEntity


@Database(
    entities = [BookMarkedArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookMarkedArticleDatabase : RoomDatabase() {
    abstract fun bookMarkedArticleDao(): BookMarkedArticleDao
}