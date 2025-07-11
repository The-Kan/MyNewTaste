package com.devyd.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devyd.local.models.CategoryWeightEntity

@Dao
interface CategoryWeightDao {
    @Query("SELECT * FROM category_weight")
    suspend fun getAll(): List<CategoryWeightEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: CategoryWeightEntity)

    @Update
    suspend fun update(entity: CategoryWeightEntity)

    @Query("DELETE FROM category_weight WHERE id = :id")
    suspend fun delete(id: Int)
}