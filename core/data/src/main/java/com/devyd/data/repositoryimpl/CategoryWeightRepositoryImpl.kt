package com.devyd.data.repositoryimpl

import com.devyd.domain.models.CategoryWeight
import com.devyd.domain.repository.CategoryWeightRepository

class CategoryWeightRepositoryImpl : CategoryWeightRepository {
    private val categoryWeightList = mutableListOf<CategoryWeight>()

    override suspend fun getAll(): List<CategoryWeight> {
        return categoryWeightList.toList()
    }

    override suspend fun add(categoryWeight: CategoryWeight) {
        categoryWeightList += categoryWeight
    }

    override suspend fun update(categoryWeight: CategoryWeight) {
        categoryWeightList.replaceAll { if(it.id == categoryWeight.id) categoryWeight else it }
    }

    override suspend fun delete(id: Int) {
        categoryWeightList.removeIf { it.id == id}
    }
}