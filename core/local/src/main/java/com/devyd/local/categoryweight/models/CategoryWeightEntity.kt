package com.devyd.local.categoryweight.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devyd.data.models.CategoryWeightDto

@Entity(tableName = "category_weight")
data class CategoryWeightEntity(
    @PrimaryKey val id: Int,
    val category: String,
    val weight: Int
)

fun CategoryWeightEntity.toDto(): CategoryWeightDto =
    CategoryWeightDto(
        id = id,
        category = category,
        weight = weight
    )

fun CategoryWeightDto.toEntity(): CategoryWeightEntity =
    CategoryWeightEntity(
        id = id,
        category = category,
        weight = weight
    )