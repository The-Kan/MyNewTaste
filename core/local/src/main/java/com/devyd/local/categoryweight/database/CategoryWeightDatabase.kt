package com.devyd.local.categoryweight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devyd.local.categoryweight.dao.CategoryWeightDao
import com.devyd.local.categoryweight.models.CategoryWeightEntity

@Database(
    entities = [CategoryWeightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CategoryWeightDatabase : RoomDatabase() {
    abstract fun categoryWeightDao(): CategoryWeightDao
}