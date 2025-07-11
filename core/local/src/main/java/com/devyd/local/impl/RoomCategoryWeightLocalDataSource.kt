package com.devyd.local.impl

import com.devyd.data.datasource.CategoryWeightLocalDataSource
import com.devyd.data.models.CategoryWeightDto
import com.devyd.local.dao.CategoryWeightDao
import com.devyd.local.models.toDto
import com.devyd.local.models.toEntity
import javax.inject.Inject

class RoomCategoryWeightLocalDataSource @Inject constructor(
    private val dao: CategoryWeightDao
) : CategoryWeightLocalDataSource {
    override suspend fun getAll(): List<CategoryWeightDto> {
        return dao.getAll().map { it.toDto() }
    }

    override suspend fun add(categoryWeightDto: CategoryWeightDto) {
        dao.add(categoryWeightDto.toEntity())
    }

    override suspend fun update(categoryWeightDto: CategoryWeightDto) {
        dao.update(categoryWeightDto.toEntity())
    }

    override suspend fun delete(categoryWeightDto: CategoryWeightDto) {
        dao.delete(categoryWeightDto.id)
    }
}