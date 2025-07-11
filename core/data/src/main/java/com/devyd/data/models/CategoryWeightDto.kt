package com.devyd.data.models

import com.devyd.domain.models.CategoryWeight

data class CategoryWeightDto(
    val id: Int,
    val category: String,
    val weight: Int
)

fun CategoryWeightDto.toDomain(): CategoryWeight =
    CategoryWeight(
        id = id,
        category = category,
        weight = weight
    )

fun CategoryWeight.toDto(): CategoryWeightDto =
    CategoryWeightDto(
        id = id,
        category = category,
        weight = weight
    )