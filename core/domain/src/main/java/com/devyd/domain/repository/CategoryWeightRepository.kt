package com.devyd.domain.repository

import com.devyd.domain.models.CategoryWeight

interface CategoryWeightRepository {
    suspend fun getAll(): List<CategoryWeight>
    suspend fun add(categoryWeight: CategoryWeight)
    suspend fun update(categoryWeight: CategoryWeight)
    suspend fun delete(categoryWeight: CategoryWeight)
}