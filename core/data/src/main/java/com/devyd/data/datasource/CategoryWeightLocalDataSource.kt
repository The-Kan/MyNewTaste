package com.devyd.data.datasource

import com.devyd.data.models.CategoryWeightDto

interface CategoryWeightLocalDataSource {
    suspend fun getAll(): List<CategoryWeightDto>
    suspend fun add(categoryWeightDto: CategoryWeightDto)
    suspend fun update(categoryWeightDto: CategoryWeightDto)
    suspend fun delete(categoryWeightDto: CategoryWeightDto)
}