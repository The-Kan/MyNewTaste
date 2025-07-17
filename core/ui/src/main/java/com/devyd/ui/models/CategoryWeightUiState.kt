package com.devyd.ui.models

import com.devyd.domain.models.CategoryWeight

data class CategoryWeightUiState(
    val id: Int,
    val category: String,
    val weight: Int
)

fun CategoryWeight.toUiState() : CategoryWeightUiState =
    CategoryWeightUiState(
        id = id,
        category = category,
        weight = weight
    )

fun CategoryWeightUiState.toDomain() : CategoryWeight =
    CategoryWeight(
        id = id,
        category = category,
        weight = weight
    )
