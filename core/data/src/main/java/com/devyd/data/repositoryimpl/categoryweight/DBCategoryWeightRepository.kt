package com.devyd.data.repositoryimpl.categoryweight

import com.devyd.data.datasource.CategoryWeightLocalDataSource
import com.devyd.data.models.toDomain
import com.devyd.data.models.toDto
import com.devyd.domain.models.CategoryWeight
import com.devyd.domain.repository.CategoryWeightRepository

class DBCategoryWeightRepository(
    private val categoryWeightLocalDataSource : CategoryWeightLocalDataSource
) : CategoryWeightRepository{
    override suspend fun getAll(): List<CategoryWeight> {
        return categoryWeightLocalDataSource.getAll().map { it.toDomain() }
    }

    override suspend fun add(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightLocalDataSource.add(categoryWeightDto)
    }

    override suspend fun update(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightLocalDataSource.update(categoryWeightDto)
    }

    override suspend fun delete(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightLocalDataSource.delete(categoryWeightDto)
    }
}