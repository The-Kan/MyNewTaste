package com.devyd.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devyd.local.dao.CategoryWeightDao
import com.devyd.local.models.CategoryWeightEntity

@Database(
    entities = [CategoryWeightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CategoryWeightDatabase : RoomDatabase() {
    abstract fun categoryWeightDao(): CategoryWeightDao
}