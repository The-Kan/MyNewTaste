package com.devyd.data.repositoryimpl.categoryweight

import com.devyd.data.models.CategoryWeightDto
import com.devyd.data.models.toDomain
import com.devyd.data.models.toDto
import com.devyd.domain.models.CategoryWeight
import com.devyd.domain.repository.CategoryWeightRepository

class InMemoryCategoryWeightRepository : CategoryWeightRepository {
    private val categoryWeightList = mutableListOf<CategoryWeightDto>()

    override suspend fun getAll(): List<CategoryWeight> {
        return categoryWeightList.map { it.toDomain() }
    }

    override suspend fun add(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightList += categoryWeightDto
    }

    override suspend fun update(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightList.replaceAll { if (it.id == categoryWeightDto.id) categoryWeightDto else it }
    }

    override suspend fun delete(categoryWeight: CategoryWeight) {
        val categoryWeightDto = categoryWeight.toDto()
        categoryWeightList.removeIf { it.id == categoryWeightDto.id }
    }
}